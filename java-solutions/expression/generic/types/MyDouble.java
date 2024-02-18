package com.example.calculator.generic.types;

public class MyDouble implements MyType<Double> {
    @Override
    public Double parseConst(String s) {
        return Double.parseDouble(s);
    }
    @Override
    public Double add(Double left, Double right) {
        return left + right;
    }
    @Override
    public Double subtract(Double left, Double right) {
        return left - right;
    }
    @Override
    public Double multiply(Double left, Double right) {
        return left * right;
    }
    @Override
    public Double divide(Double left, Double right) {
        return left / right;
    }
    @Override
    public Double negate(Double element) {
        return -element;
    }
    @Override
    public Double fromInt(int x) {
        return (double)x;
    }

    @Override
    public Double count(Double x) {
        long y = Double.doubleToLongBits(x);
        return (double)Long.bitCount(y);
    }

    @Override
    public Double max(Double left, Double right) {
        if (Double.compare(left, right) > 0) {
            return left;
        } else {
            return right;
        }
    }

    @Override
    public Double min(Double left, Double right) {
        if (left.isNaN()) {
            return left;
        }
        if (right.isNaN()) {
            return right;
        }
        if (Double.compare(left, right) < 0) {
            return left;
        } else {
            return right;
        }
    }
}
