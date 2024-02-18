package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Negate<T extends Number> implements TripleExpression<T> {
    protected final TripleExpression<T> expression;

    public Negate(TripleExpression<T> expression) {
        this.expression = expression;
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return type.negate(expression.evaluate(type, x, y, z));
    }

    @Override
    public String toString() {
        return "-" + expression;
    }
}
