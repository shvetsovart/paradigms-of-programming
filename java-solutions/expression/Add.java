package com.example.calculator;

public class Add extends AbstractOperation {
    public Add(Expression left, Expression right) {
        super(left, right, '+');
    }

    @Override
    public int evaluateImpl(int leftInt, int rightInt) {
        return leftInt + rightInt;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x) + right.evaluate(x);
    }
}
