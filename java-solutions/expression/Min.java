package com.example.calculator;

public class Min implements TripleExpression {
    TripleExpression left, right;
    public Min(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int leftInt = left.evaluate(x, y, z);
        int rightInt = right.evaluate(x, y, z);
        if (leftInt <= rightInt) {
            return leftInt;
        } else {
            return rightInt;
        }
    }

    @Override
    public String toString() {
        return "(" + left + " min " + right + ")";
    }
}
