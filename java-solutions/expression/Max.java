package com.example.calculator;

public class Max implements TripleExpression {
    TripleExpression left, right;
    public Max(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int leftInt = left.evaluate(x, y, z);
        int rightInt = right.evaluate(x, y, z);
        if (rightInt >= leftInt) {
            return rightInt;
        } else {
            return leftInt;
        }
    }

    @Override
    public String toString() {
        return "(" + left + " max " + right + ")";
    }
}
