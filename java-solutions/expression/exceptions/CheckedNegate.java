package com.example.calculator.exceptions;

import expression.OverflowException;
import expression.TripleExpression;

public class CheckedNegate implements TripleExpression {
    protected final TripleExpression expression;

    public CheckedNegate(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int result = expression.evaluate(x, y, z);
        if (result == Integer.MIN_VALUE) {
            throw new OverflowException("-(" + expression + ")");
        } else {
            return -expression.evaluate(x, y, z);
        }
    }

    @Override
    public String toString() {
        return "-" + expression;
    }
}
