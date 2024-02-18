package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

public interface TripleExpression<T extends Number> {
    T evaluate(MyType<T> type, T x, T y, T z);
}
