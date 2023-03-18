package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.ADT.MyILatch;
import Model.Expression.ValueExp;
import Model.PrgState.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Exception.MyException;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public CountDownStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyILatch latchTable = state.getLatchTable();
        if (symTable.isDefined(var)) {
            IntValue fi = (IntValue) symTable.lookup(var);
            int foundIndex = fi.getVal();
            if (latchTable.containsKey(foundIndex)) {
                if (latchTable.get(foundIndex) > 0) {
                    latchTable.update(foundIndex, latchTable.get(foundIndex) - 1);
                }
                state.getExeStack().push(new PrintStmt(new ValueExp(new IntValue(state.getId()))));
            } else {
                throw new MyException("Index not found in the latch table!");
            }
        } else {
            throw new MyException("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        }
        throw new MyException(String.format("%s is not of int type!", var));
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }

    @Override
    public String toString() {
        return "CountDown(" + var + ")";
    }
}
