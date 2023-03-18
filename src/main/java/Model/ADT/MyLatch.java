package Model.ADT;

import Model.Value.Value;
import Exception.MyException;
import java.util.HashMap;

public class MyLatch implements MyILatch{
    HashMap<Integer, Integer> latchTable;
    Integer freeLocationValue;

    public MyLatch() {
        this.latchTable = new HashMap<>();
        this.freeLocationValue = 1;
    }

    @Override
    public void add(int key, int value) throws MyException {
        synchronized (this) {
            if (!latchTable.containsKey(key)) {
                latchTable.put(key, value);
            } else {
                throw new MyException(String.format("Latch table already contains the key %d!", key));
            }
        }
    }

    @Override
    public void update(int key, int value) throws MyException {
        synchronized (this) {
            if (latchTable.containsKey(key)) {
                latchTable.replace(key, value);
            } else {
                throw new MyException(String.format("Latch table doesn't contain the key %d!", key));
            }
        }
    }

    @Override
    public int get(int key) throws MyException {
        synchronized (this){
            return latchTable.get(key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return latchTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeLocationValue++;
            return freeLocationValue;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeLocationValue = freeAddress;
        }
    }

    @Override
    public HashMap<Integer, Integer> getLatchTable() {
        synchronized (this) {
            return latchTable;
        }
    }

    @Override
    public void setLatchTable(HashMap<Integer, Integer> newLatchTable) {
        synchronized (this) {
             this.latchTable = newLatchTable;
        }
    }

    @Override
    public String toString() {
        return latchTable.toString();
    }
}
