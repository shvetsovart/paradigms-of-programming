package com.example.calculator;

import java.util.Objects;

public class Const implements TripleExpression, Expression {
    private final int x;

    public Const(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return x == aConst.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.x;
    }

    @Override
    public int evaluate(int x) {
        return this.x;
    }
}
