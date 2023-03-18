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
import java.io.IOException;

public class CloseReadFile implements IStmt{
    Exp exp;

    public CloseReadFile(Exp exp) {
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
        if (!value.getType().equals(new StringType()))
            throw new MyException(exp.toString() + "%s does not evaluate to StringValue");
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getVal()))
            throw new MyException(value + " is not present in the FileTable");
        BufferedReader br = fileTable.lookup(fileName.getVal());
        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Unexpected error in closing " + value);
        }
        fileTable.remove(fileName.getVal());
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseReadFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "CloseReadFile(" + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("CloseReadFile requires a string expression.");
    }
}
