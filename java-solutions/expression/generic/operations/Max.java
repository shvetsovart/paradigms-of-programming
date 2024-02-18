package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Max<T extends Number> implements TripleExpression<T> {
    TripleExpression<T> left, right;
    public Max(TripleExpression<T> left, TripleExpression<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + " max " + right + ")";
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        T leftValue = left.evaluate(type, x, y, z), rightValue = right.evaluate(type, x, y, z);
        return type.max(leftValue, rightValue);
    }
}
