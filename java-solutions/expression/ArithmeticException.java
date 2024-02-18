package com.example.calculator;

public class ArithmeticException extends ParseExpressionException {

    public ArithmeticException(String message) {
        super("Arithmetic -> " + message);
    }
}
