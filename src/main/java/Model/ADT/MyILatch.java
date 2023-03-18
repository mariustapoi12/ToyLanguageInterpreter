package Model.ADT;

import java.util.HashMap;
import Exception.MyException;

public interface MyILatch {
    void add(int key, int value) throws MyException;
    void update(int key, int value) throws MyException;
    int get(int key) throws MyException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    HashMap<Integer, Integer> getLatchTable();
    void setLatchTable(HashMap<Integer, Integer> newLatchTable);
}
