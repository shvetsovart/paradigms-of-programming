package com.example.calculator;

import java.util.Objects;

public abstract class CheckedAbstractOperation implements TripleExpression {
    protected TripleExpression left, right;
    char sign;

    public CheckedAbstractOperation(TripleExpression left, TripleExpression right, char sign) {
        this.left = left;
        this.right = right;
        this.sign = sign;
    }

    public CheckedAbstractOperation() {

    }

    @Override
    public String toString() {
        return "(" + left + " " + sign + " " + right + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckedAbstractOperation that = (CheckedAbstractOperation) o;
        return sign == that.sign && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, sign);
    }

    public int evaluate(int x, int y, int z) {
        int leftInt = left.evaluate(x, y, z), rightInt = right.evaluate(x, y, z);
        return evaluateImpl(leftInt, rightInt);
    }

    protected abstract int evaluateImpl(int leftInt, int rightInt);
}
