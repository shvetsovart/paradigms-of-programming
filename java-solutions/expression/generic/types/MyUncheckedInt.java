package com.example.calculator.generic.types;

public class MyUncheckedInt implements MyType<Integer> {

    @Override
    public Integer parseConst(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public Integer add(Integer left, Integer right) {
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) {
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        return left / right;
    }

    @Override
    public Integer negate(Integer element) {
        return -element;
    }

    @Override
    public Integer fromInt(int x) {
        return x;
    }

    @Override
    public Integer count(Integer x) {
        return Integer.bitCount(x);
    }

    @Override
    public Integer max(Integer left, Integer right) {
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    @Override
    public Integer min(Integer left, Integer right) {
        if (left < right) {
            return left;
        } else {
            return right;
        }
    }
}
