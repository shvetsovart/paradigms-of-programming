package com.example.calculator.exceptions;

import expression.CheckedAbstractOperation;
import expression.OverflowException;
import expression.TripleExpression;

public class CheckedAdd extends CheckedAbstractOperation {
    public CheckedAdd(TripleExpression left, TripleExpression right) {
        super(left, right, '+');
    }

    @Override
    public int evaluateImpl(int leftInt, int rightInt) {
        if ((rightInt >= 0) != (leftInt + rightInt >= leftInt)) {
            throw new OverflowException(left + " + " + right);
        } else {
            return leftInt + rightInt;
        }
    }
}
