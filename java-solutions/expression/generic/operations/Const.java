package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Const<T extends Number> implements TripleExpression<T> {
    private final String x;

    public Const(String x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }

    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.parseConst(this.x);
    }
}
