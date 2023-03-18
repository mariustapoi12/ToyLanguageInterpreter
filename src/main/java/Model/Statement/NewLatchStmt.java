package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyILatch;
import Model.Expression.Exp;
import Model.PrgState.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Exception.MyException;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt{
    private final String var;
    private final Exp exp;

    private static final Lock lock = new ReentrantLock();

    public NewLatchStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyILatch latchTable = state.getLatchTable();
        IntValue nr = (IntValue) (exp.eval(symTable, heap));
        int number = nr.getVal();
        int freeAddress = latchTable.getFreeAddress();
        latchTable.add(freeAddress, number);
        if (symTable.isDefined(var)) {
            symTable.update(var, new IntValue(freeAddress));
        } else {
            throw new MyException(String.format("%s is not defined in the symbol table!", var));
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp.typeCheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else {
                throw new MyException("Expression doesn't have the int type!");
            }
        } else {
            throw new MyException(String.format("%s is not of int type!", var));
        }
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp + ")";
    }
}
