package com.example.calculator.exceptions;

import expression.TripleExpression;

public class CheckedBracketNegate extends CheckedNegate {
    public CheckedBracketNegate(TripleExpression expression) {
        super(expression);
    }

    @Override
    public String toString() {
        return "-(" + expression + ")";
    }
}
