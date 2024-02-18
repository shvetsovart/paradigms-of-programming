package com.example.calculator.generic.operations;

import java.util.Objects;

public abstract class AbstractOperation<T extends Number> implements TripleExpression<T> {
    protected TripleExpression<T> left, right;
    char sign;

    public AbstractOperation(TripleExpression<T> left, TripleExpression<T> right, char sign) {
        this.left = left;
        this.right = right;
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "(" + left + " " + sign + " " + right + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // :NOTE: raw use of generics
        AbstractOperation<?> that = (AbstractOperation<?>) o;
        return sign == that.sign && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, sign);
    }
}
