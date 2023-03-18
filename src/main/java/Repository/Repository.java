package Repository;

import Model.PrgState.PrgState;
import Exception.MyException;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private final String logFilePath;

    public Repository(PrgState prgStates, String logFilePath) {
        this.prgStates = new ArrayList<>();
        this.logFilePath = logFilePath;
        this.addPrgState(prgStates);
        this.emptyLogFile();
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStates;
    }

    @Override
    public void setPrgList(List<PrgState> programStates) {
        this.prgStates = programStates;
    }

    @Override
    public void addPrgState(PrgState state) {
        this.prgStates.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException {
        try {
            PrintWriter logFile;
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(prgState.toString());
            logFile.close();
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    @Override
    public void emptyLogFile() throws MyException {
        try {
            PrintWriter logFile;
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
