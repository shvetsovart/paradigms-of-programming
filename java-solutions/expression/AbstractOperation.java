package com.example.calculator;

import java.util.Objects;

public abstract class AbstractOperation implements Expression, TripleExpression {
    protected Expression left, right;
    char sign;

    public AbstractOperation(Expression left, Expression right, char sign) {
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
        AbstractOperation that = (AbstractOperation) o;
        return sign == that.sign && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, sign);
    }

    public int evaluate(int x) {
        int leftInt = left.evaluate(x), rightInt = right.evaluate(x);
        return evaluateImpl(leftInt, rightInt);
    }

    protected abstract int evaluateImpl(int leftInt, int rightInt);
}
