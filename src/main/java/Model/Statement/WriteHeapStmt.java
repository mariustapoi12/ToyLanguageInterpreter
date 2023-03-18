package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStmt implements IStmt{
    String varName;
    Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if(!symTable.isDefined(varName))
            throw new MyException(varName + "is not in symTable!");
        Value varValue = symTable.lookup(varName);
        if(!(varValue.getType() instanceof RefType))
            throw new MyException(varName + "is not of RefType!");
        RefValue refValue = (RefValue) varValue;
        Value evaluated = exp.eval(symTable, heap);
        if(!evaluated.getType().equals(refValue.getLocationType()))
            throw new MyException(evaluated + " not of " + refValue.getLocationType() + "!");
        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "WriteHeap(" + varName + ", " + exp.toString() +")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(varName).equals(new RefType(exp.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new MyException("WriteHeap: right hand side and left hand side have different types.");
    }
}
