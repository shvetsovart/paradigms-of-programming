package com.example.calculator;

public class Subtract extends AbstractOperation implements TripleExpression {
    public Subtract(Expression left, Expression right) {
        super(left, right, '-');
    }

    @Override
    public int evaluateImpl(int leftInt, int rightInt) {
        return leftInt - rightInt;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return 0;
    }
}
