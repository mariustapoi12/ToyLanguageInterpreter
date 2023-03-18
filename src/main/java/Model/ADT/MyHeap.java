package Model.ADT;

import Model.Value.Value;
import Exception.MyException;

import java.util.HashMap;
import java.util.Set;

public class MyHeap implements MyIHeap{
    HashMap<Integer, Value> heap;
    Integer freeLocationValue;

    public MyHeap() {
        this.heap = new HashMap<>();
        freeLocationValue = 1;
    }

    public HashMap<Integer, Value> getContent() {
        return heap;
    }

    public Integer getFreeLocationValue() {
        return freeLocationValue;
    }

    public void setContent(HashMap<Integer, Value> heap) {
        this.heap = heap;
    }

    public int newValue(){
        freeLocationValue += 1;
        while (freeLocationValue == 0 || heap.containsKey(freeLocationValue))
            freeLocationValue += 1;
        return freeLocationValue;
    }

    @Override
    public int add(Value value) {
        heap.put(freeLocationValue, value);
        Integer location = freeLocationValue;
        freeLocationValue = newValue();
        return location;
    }

    @Override
    public void update(Integer position, Value value) throws MyException {
        if(!heap.containsKey(position))
            throw new MyException("Invalid position!");
        heap.put(position, value);
    }

    @Override
    public Value get(Integer position) throws MyException {
        if(!heap.containsKey(position))
            throw new MyException("Invalid position!");
        return heap.get(position);
    }

    @Override
    public boolean containsKey(Integer position) {
        return this.heap.containsKey(position);
    }

    @Override
    public void remove(Integer key) throws MyException {
        if (!containsKey(key))
            throw new MyException(key + "is not defined");
        freeLocationValue = key;
        this.heap.remove(key);
    }

    @Override
    public Set<Integer> keySet() {
        return heap.keySet();
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }
}
