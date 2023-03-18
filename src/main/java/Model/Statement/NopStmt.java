package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.Type;

public class NopStmt implements IStmt{
    public NopStmt() {
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public String toString() {
        return " ";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
