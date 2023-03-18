package Model.ADT;

import Exception.MyException;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Set;

public interface MyIHeap {
    int add(Value value);
    void update(Integer position, Value value) throws MyException;
    Value get(Integer position) throws MyException;
    boolean containsKey(Integer position);
    void remove(Integer key) throws MyException;
    Set<Integer> keySet();

    HashMap<Integer, Value> getContent();
    void setContent(HashMap<Integer, Value> newMap);
}
