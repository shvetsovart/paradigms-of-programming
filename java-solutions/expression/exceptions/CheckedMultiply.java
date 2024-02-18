package com.example.calculator.exceptions;

import expression.CheckedAbstractOperation;
import expression.OverflowException;
import expression.TripleExpression;

public class CheckedMultiply extends CheckedAbstractOperation {
    public CheckedMultiply(TripleExpression left, TripleExpression right) {
        super(left, right, '*');
    }

    @Override
    public int evaluateImpl(int leftInt, int rightInt) {
        if (leftInt == Integer.MIN_VALUE && rightInt == -1 ||
                leftInt != 0 && rightInt != 0 && (leftInt * rightInt) / rightInt != leftInt) {
            throw new OverflowException(left + " * " + right);
        } else {
            return leftInt * rightInt;
        }
    }
}
