package com.example.calculator;

public class Multiply extends AbstractOperation {
    public Multiply(Expression left, Expression right) {
        super(left, right, '*');
    }

    @Override
    public int evaluateImpl(int leftInt, int rightInt) {
        return leftInt * rightInt;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x) * right.evaluate(x);
    }
}
