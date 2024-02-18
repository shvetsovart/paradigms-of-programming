package com.example.calculator.generic.operations;

import expression.generic.types.MyType;

import java.util.Objects;

public class Variable<T extends Number> implements TripleExpression<T> {
    private final String x;

    public Variable(String x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return this.x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable variable) {
            return this.x.equals(variable.x);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }

    @Override
    public T evaluate(MyType<T> type, T x, T y, T z) {
        return switch (this.x) {
            case "y" -> y;
            case "z" -> z;
            default -> x;
        };
    }
}
