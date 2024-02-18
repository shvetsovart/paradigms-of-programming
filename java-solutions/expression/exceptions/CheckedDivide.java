package com.example.calculator.exceptions;

import expression.CheckedAbstractOperation;
import expression.DivideByZeroException;
import expression.OverflowException;
import expression.TripleExpression;

public class CheckedDivide extends CheckedAbstractOperation {
    public CheckedDivide(TripleExpression left, TripleExpression right) {
        super(left, right, '/');
    }

    @Override
    public int evaluateImpl(int leftInt, int rightInt) {
        if (rightInt == 0) {
            throw new DivideByZeroException(left + " " + right);
        }
        if ((leftInt == Integer.MIN_VALUE) && (rightInt == -1)) {
            throw new OverflowException(left + " / " + right);
        }
        return leftInt / rightInt;
    }
}
