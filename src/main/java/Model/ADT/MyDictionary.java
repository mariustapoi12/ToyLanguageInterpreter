package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    Map<K, V> myDict;

    public MyDictionary(Map<K, V> myDict) {
        this.myDict = myDict;
    }

    public Map<K, V> getContent() {
        return myDict;
    }

    public void setMyDict(Map<K, V> myDict) {
        this.myDict = myDict;
    }

    @Override
    public void add(K key, V val) {
        myDict.put(key, val);
    }

    @Override
    public void remove(K key) {
        myDict.remove(key);
    }

    @Override
    public void update(K key, V val) {
        add(key, val);
    }

    @Override
    public V lookup(K key) {
        return myDict.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return myDict.get(key) != null;
    }

    @Override
    public String toString() {
        return myDict.toString();
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> toReturn = new MyDictionary<>(new HashMap<>());
        for (K key: myDict.keySet())
            toReturn.add(key, lookup(key));
        return toReturn;
    }
}
