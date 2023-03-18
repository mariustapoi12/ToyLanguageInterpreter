package Model.Statement;

import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Value.Value;
import Model.ADT.MyIDictionary;
import Model.Type.Type;
public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        //MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String,Value> symTbl= state.getSymTable();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, state.getHeap());
            Type typeId = (symTbl.lookup(id)).getType();
            if ((val.getType()).equals(typeId)) {
                symTbl.update(id, val);
            }
            else{
                throw new MyException("Declared type of variable" + id + " and type of  the assigned expression do not match");
            }
        }
        else{
            throw new MyException("The used variable" + id + " was not declared before");
        }
        state.setSymTable(symTbl);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typeCheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types");
    }
}
