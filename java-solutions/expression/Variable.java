package com.example.calculator;

import java.util.Objects;

public class Variable implements TripleExpression, Expression {
    private final String x;

    public Variable(String x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return this.x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable variable) {
            return this.x.equals(variable.x);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (this.x) {
            case "y" -> y;
            case "z" -> z;
            default -> x;
        };
    }

    @Override
    public int evaluate(int x) {
        return x;
    }
}
