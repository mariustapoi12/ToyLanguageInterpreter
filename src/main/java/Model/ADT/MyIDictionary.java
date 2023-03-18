package Model.ADT;

import java.util.Map;

public interface MyIDictionary<K, V> {
    void add(K key, V val);
    void update(K key, V val);

    void remove(K key);

    V lookup(K key);

    boolean isDefined(K key);

    Map<K, V> getContent();

    MyIDictionary<K, V> deepCopy();
}
