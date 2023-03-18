package Model.Statement;

import Model.ADT.MyDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ForkStmt implements IStmt{
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>(new Stack<>());
        newStack.push(stmt);
        MyIDictionary<String, Value> newSymTbl = new MyDictionary<>(new HashMap<>());
        for(Map.Entry<String, Value> entry: state.getSymTable().getContent().entrySet())
            newSymTbl.add(entry.getKey(), entry.getValue().deepCopy());
        return new PrgState(newStack, newSymTbl, state.getOut(), state.getHeap(), state.getFileTable(), state.getLatchTable());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "Fork(" + stmt.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
