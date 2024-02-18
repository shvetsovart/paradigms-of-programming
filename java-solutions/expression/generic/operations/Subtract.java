package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Subtract<T extends Number> extends AbstractOperation<T> {
    public Subtract(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, '-');
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.subtract(left.evaluate(type, x, y, z), right.evaluate(type, x, y, z));
    }
}
