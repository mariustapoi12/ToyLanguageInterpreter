package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = null;
        try {
            value = exp.eval(state.getSymTable(), state.getHeap());
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        if (value.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getVal())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getVal()));
                } catch (FileNotFoundException e) {
                    throw new MyException(fileName.getVal() + " could not be opened");
                }
                fileTable.add(fileName.getVal(), br);
                state.setFileTable(fileTable);
            } else {
                throw new MyException(fileName.getVal() + " is already opened");
            }
        } else {
            throw new MyException(exp.toString() + " does not evaluate to StringType");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenRFile: " + exp.toString();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenReadFile requires a string expression.");
    }
}
