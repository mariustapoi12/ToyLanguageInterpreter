package Model.Statement;

import Exception.MyException;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> list = state.getOut();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        try {
            list.add(exp.eval(symTbl, state.getHeap()));
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        state.setOut(list);
        return null;
    }

    @Override
    public String toString() {
        return "print(" +exp.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }
}
