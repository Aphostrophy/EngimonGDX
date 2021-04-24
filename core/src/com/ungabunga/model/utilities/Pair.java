package com.ungabunga.model.utilities;

public class Pair<T, P> {
    private T first;
    private P second;

    public Pair(){

    }

    public Pair( T f, P s ) {
        first = f;
        second = s;
    }
    public Pair(Pair<T, P> pair) {
        first = pair.first;
        second = pair.second;
    }
    public T getFirst() { return first; }
    public P getSecond() { return second; }
    public void setFirst( T f ) { first = f; }
    public void setSecond( P s ) { second = s; }

    public String toString( ) { return "(" + first + ", " + second + ")"; }
}
