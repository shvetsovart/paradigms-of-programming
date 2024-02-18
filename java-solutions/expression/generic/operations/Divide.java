package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Divide<T extends Number> extends AbstractOperation<T> {
    public Divide(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, '/');
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.divide(left.evaluate(type, x, y, z), right.evaluate(type, x, y, z));
    }
}
