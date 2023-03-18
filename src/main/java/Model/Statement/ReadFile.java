package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Exception.MyException;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    Exp exp;
    String var_name;

    public ReadFile(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(var_name)) {
            Value value = symTable.lookup(var_name);
            if (value.getType().equals(new IntType())) {
                try {
                    value = exp.eval(symTable, state.getHeap());
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue.getVal())) {
                        BufferedReader br = fileTable.lookup(castValue.getVal());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.add(var_name, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new MyException("Could not read from file " + castValue);
                        }
                    } else {
                        throw new MyException("The file table does not contain " + castValue);
                    }
                } else {
                    throw new MyException(value + " does not evaluate to StringType");
                }
            } else {
                throw new MyException(value + " is not of type IntType");
            }
        } else {
            throw new MyException(var_name + " is not present in the symTable.");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), var_name);
    }

    @Override
    public String toString() {
        return "ReadFile(" + exp.toString() + ", " + var_name + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new StringType()))
            if (typeEnv.lookup(var_name).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("ReadFile requires an int as its variable parameter.");
        else
            throw new MyException("ReadFile requires a string as es expression parameter.");
    }
}
