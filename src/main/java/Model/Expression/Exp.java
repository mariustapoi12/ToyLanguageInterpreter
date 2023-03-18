package Model.Expression;

import Model.ADT.MyIHeap;
import Model.Type.Type;
import Model.Value.Value;
import Model.ADT.MyIDictionary;
import Exception.MyException;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws MyException;

    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    Exp deepCopy();
}
