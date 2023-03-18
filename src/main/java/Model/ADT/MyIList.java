package Model.ADT;

import java.util.List;

public interface MyIList<E> {
    void add(E elem);
    void remove(int pos);
    E get(int pos);

    List<E> getMyList();
}
