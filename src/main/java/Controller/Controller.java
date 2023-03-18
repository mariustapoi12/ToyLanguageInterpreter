package Controller;

import Model.ADT.MyIStack;
import Model.PrgState.PrgState;
import Model.Statement.IStmt;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;
import Exception.MyException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repository;

    ExecutorService executorService;
    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addPrg(PrgState prg){
        repository.addPrgState(prg);
    }

    public Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream().
                filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgStates) throws InterruptedException{
        prgStates.forEach(prgState -> repository.logPrgStateExec(prgState));
        List<Callable<PrgState>> callList = prgStates.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep(p);}))
                .collect(Collectors.toList());
        List<PrgState> newProgramList = executorService.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        prgStates.addAll(newProgramList);
        prgStates.forEach(prgState -> {repository.logPrgStateExec(prgState); /*System.out.println(prgState);*/});
        repository.setPrgList(prgStates);
    }

    public void oneStep() throws MyException, InterruptedException {
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        oneStepForAllPrg(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executorService.shutdownNow();
        //repository.setProgramStates(programStates);
    }

    public void allStep() throws ExecutionException, InterruptedException {
        executorService = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState>  prgList=removeCompletedPrg(repository.getPrgList());
//        if(prgList.size() > 0)
//            prgList.forEach(prgState -> System.out.println(prgState));
        while(prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList=removeCompletedPrg(repository.getPrgList());
        }
        executorService.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository
        // update the repository state
        repository.setPrgList(prgList);
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().getContent().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }
    public void setProgramStates(List<PrgState> programStates) {
        this.repository.setPrgList(programStates);
    }
    public List<PrgState> getProgramStates() {
        return this.repository.getPrgList();
    }
}
