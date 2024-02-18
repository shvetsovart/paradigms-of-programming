package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Count<T extends Number> implements TripleExpression<T> {
    protected final TripleExpression<T> expression;

    public Count(TripleExpression<T> expression) {
        this.expression = expression;
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.count(expression.evaluate(type, x, y, z));
    }
}
