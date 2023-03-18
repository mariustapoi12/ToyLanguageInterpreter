package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStmt implements IStmt{
    String varName;
    Exp exp;

    public NewStmt(String varName, Exp exp) {
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
        Value evaluated = exp.eval(symTable, heap);
        Type locationType = ((RefValue)varValue).getLocationType();
        if(!locationType.equals(evaluated.getType()))
            throw new MyException(varName + " not of " + evaluated.getType() + "!");
        int newPosition = heap.add(evaluated);
        symTable.add(varName, new RefValue(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "New("+varName+", "+exp.toString()+ ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varName);
        Type typexp = exp.typeCheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types.");
    }
}
