package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Type.Type;
import Model.Type.BoolType;
import Exception.MyException;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString()+" "+name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(name))
        {
            throw new MyException("Variable is already declared");
        }
        else
            symTbl.add(name, type.defaultValue());

        state.setSymTable(symTbl);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name,type);
        return typeEnv;
    }
}
