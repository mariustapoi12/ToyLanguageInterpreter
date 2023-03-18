package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement.

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    IStmt deepCopy();
}