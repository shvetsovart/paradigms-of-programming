package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Add<T extends Number> extends AbstractOperation<T> {
    public Add(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, '+');
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.add(left.evaluate(type, x, y, z), right.evaluate(type, x, y, z));
    }
}
