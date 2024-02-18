package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public class Min<T extends Number> implements TripleExpression<T> {
    TripleExpression<T> left, right;
    public Min(TripleExpression<T> left, TripleExpression<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        T leftValue = left.evaluate(type, x, y, z), rightValue = right.evaluate(type, x, y, z);
        return type.min(leftValue, rightValue);
    }

    @Override
    public String toString() {
        return "(" + left + " min " + right + ")";
    }
}
