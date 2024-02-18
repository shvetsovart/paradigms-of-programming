package com.example.calculator.generic.types;

public interface MyType<T extends Number> {
    T add(T left, T right);

    T subtract(T left, T right);

    T multiply(T left, T right);

    T divide(T left, T right);

    T negate(T element);

    T parseConst(String s);

    T fromInt(int x);

    T count(T x);

    T max(T left, T right);

    T min(T left, T right);
}
