package com.example.calculator;

public class DivideByZeroException extends java.lang.ArithmeticException {
    public DivideByZeroException(String message) {
        super("Division by zero exception: " + message);
    }
}
