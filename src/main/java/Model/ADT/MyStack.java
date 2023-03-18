package Model.ADT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> myStack; //a field whose type CollectionType is an appropriate generic java library  collection

    public MyStack(Stack<T> myStack) {
        this.myStack = myStack;
    }

    public Stack<T> getMyStack() {
        return myStack;
    }

    public void setMyStack(Stack<T> myStack) {
        this.myStack = myStack;
    }

    @Override
    public T pop() {
        return myStack.pop();
    }

    @Override
    public void push(T v) {
        myStack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return myStack.isEmpty();
    }

    @Override
    public String toString() {
        return myStack.toString();
    }

    @Override
    public List<T> getReversed() {
        List<T> list = Arrays.asList((T[]) myStack.toArray());
        Collections.reverse(list);
        return list;
    }
}
