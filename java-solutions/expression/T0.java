package com.example.calculator;

public class T0 implements TripleExpression {
    private final TripleExpression expression;
    boolean hasBrackets;

    public T0(TripleExpression expression, boolean hasBrackets) {
        this.expression = expression;
        this.hasBrackets = hasBrackets;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int value = expression.evaluate(x, y, z);
        return Integer.numberOfTrailingZeros(value);
    }

    @Override
    public String toString() {
        if (hasBrackets) {
            return "t0(" + expression + ")";
        } else {
            return "t0" + expression;
        }
    }
}
