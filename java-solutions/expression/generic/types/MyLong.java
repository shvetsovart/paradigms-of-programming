package com.example.calculator.generic.types;

public class MyLong implements MyType<Long> {

    @Override
    public Long add(Long left, Long right) {
        return left + right;
    }

    @Override
    public Long subtract(Long left, Long right) {
        return left - right;
    }

    @Override
    public Long multiply(Long left, Long right) {
        return left * right;
    }

    @Override
    public Long divide(Long left, Long right) {
        return left / right;
    }

    @Override
    public Long negate(Long element) {
        return -element;
    }

    @Override
    public Long parseConst(String s) {
        return Long.parseLong(s);
    }

    @Override
    public Long fromInt(int x) {
        return (long)x;
    }

    @Override
    public Long count(Long x) {
        return (long)Long.bitCount(x);
    }

    @Override
    public Long max(Long left, Long right) {
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    @Override
    public Long min(Long left, Long right) {
        if (left < right) {
            return left;
        } else {
            return right;
        }
    }
}
