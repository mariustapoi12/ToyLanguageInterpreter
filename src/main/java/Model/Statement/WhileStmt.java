package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStmt implements IStmt{
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getExeStack();
        if(!value.getType().equals(new BoolType()))
            throw new MyException(value + "is not of BoolType!");
        BoolValue boolValue = (BoolValue) value;
        if(boolValue.getVal()){
            stack.push(this);
            stack.push(stmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "while("+exp.toString()+"){"+stmt.toString()+"}";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool");
    }
}
