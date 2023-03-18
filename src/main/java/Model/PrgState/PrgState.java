package Model.PrgState;

import Model.ADT.*;
import Model.Statement.IStmt;
import Model.Value.IntValue;
import Model.Value.Value;
import java.io.BufferedReader;
import Exception.MyException;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;

    MyIHeap heap;
    IStmt originalProgram;

    MyIDictionary<String, BufferedReader> fileTable;

    MyILatch latchTable;

    int id;

    static int lastId = 0;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIHeap heap, IStmt prg, MyIDictionary<String, BufferedReader> fileTable, MyILatch latchTable) {
        this.exeStack =stk;
        this.symTable=symtbl;
        this.out = ot;
        this.heap = heap;
        this.fileTable = fileTable;
        this.originalProgram= prg.deepCopy();//recreate the entire original prg
        exeStack.push(prg);
        this.id = setId();
        this.latchTable = latchTable;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIHeap heap, MyIDictionary<String, BufferedReader> fileTable, MyILatch latchTable) {
        this.exeStack =stk;
        this.symTable=symtbl;
        this.out = ot;
        this.heap = heap;
        this.fileTable = fileTable;
        this.id = setId();
        this.latchTable = latchTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString() {
        return "ID:" + id + "\nPrgState:\n" +
                "Execution Stack: " + exeStack.toString() + "\n" +
                "Symobls Table: " + symTable.toString() + "\n" +
                "Output: " + out.toString() + "\n" +
                "Heap memory: " + heap.toString() + "\n" +
                "File Table: " + fileTable.toString() + "\n" +
                "Latch Table: " + latchTable.toString();
    }

    public boolean isNotCompleted() {
        return exeStack.isEmpty();
    }
    public PrgState oneStep(PrgState state) throws MyException {
        if (exeStack.isEmpty())
            throw new MyException("Program state stack is empty!");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public int getId() {
        return this.id;
    }

    public MyILatch getLatchTable() {
        return latchTable;
    }

    public void setLatchTable(MyILatch latchTable) {
        this.latchTable = latchTable;
    }
}
