package com.example.calculator;

public class OverflowException extends ArithmeticException {
    public OverflowException(String message) {
        super("Overflow exception: " + message);
    }
}
