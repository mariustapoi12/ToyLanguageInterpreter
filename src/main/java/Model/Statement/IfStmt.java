package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.ADT.MyIStack;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        Value cond = null;
        try {
            cond = exp.eval(symTbl, state.getHeap());
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        if (cond.getType().equals(new BoolType())) {
            BoolValue boolCond = (BoolValue) cond;
            if(boolCond.getVal())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        else{
            throw new MyException("Conditional expression is not a boolean!");
        }
        state.setExeStack(stk);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
