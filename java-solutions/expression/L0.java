package com.example.calculator;

public class L0 implements TripleExpression {
    private final TripleExpression expression;
    boolean hasBrackets;

    public L0(TripleExpression expression, boolean hasBrackets) {
        this.expression = expression;
        this.hasBrackets = hasBrackets;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int value = expression.evaluate(x, y, z);
        return Integer.numberOfLeadingZeros(value);
    }

    @Override
    public String toString() {
        if (hasBrackets) {
            return "l0(" + expression + ")";
        } else {
            return "l0 " + expression;
        }
    }
}
