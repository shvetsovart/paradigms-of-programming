package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Multiply<T extends Number> extends AbstractOperation<T> {
    public Multiply(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, '*');
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.multiply(left.evaluate(type, x, y, z), right.evaluate(type, x, y, z));
    }
}
