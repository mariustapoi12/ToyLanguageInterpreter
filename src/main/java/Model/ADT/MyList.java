package Model.ADT;

import java.util.List;

public class MyList<E> implements MyIList<E> {
    List<E> myList;

    public MyList(List<E> myList) {
        this.myList = myList;
    }

    @Override
    public List<E> getMyList() {
        return myList;
    }

    public void setMyList(List<E> myList) {
        this.myList = myList;
    }

    @Override
    public void add(E elem) {
        myList.add(elem);
    }

    @Override
    public void remove(int pos) {
        myList.remove(pos);
    }

    @Override
    public E get(int pos) {
        return myList.get(pos);
    }

    @Override
    public String toString() {
        return myList.toString();
    }
}
