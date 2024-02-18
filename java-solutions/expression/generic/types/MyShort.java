package com.example.calculator.generic.types;

public class MyShort implements MyType<Short> {

    @Override
    public Short add(Short left, Short right) {
        return (short)(left + right);
    }

    @Override
    public Short subtract(Short left, Short right) {
        return (short)(left - right);
    }

    @Override
    public Short multiply(Short left, Short right) {
        return (short)(left * right);
    }

    @Override
    public Short divide(Short left, Short right) {
        return (short)(left / right);
    }

    @Override
    public Short negate(Short element) {
        return (short)(-element);
    }

    @Override
    public Short parseConst(String s) {
        return Short.parseShort(s);
    }

    @Override
    public Short fromInt(int x) {
        return (short)x;
    }

    @Override
    public Short count(Short x) {
        int temp = x + 65536;
        short count = 0;
        for (int i = 0; i < 16; i++) {
            count += (temp >> (15 - i)) % 2;
        }
        return count;
    }

    @Override
    public Short max(Short left, Short right) {
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    @Override
    public Short min(Short left, Short right) {
        if (left < right) {
            return left;
        } else {
            return right;
        }
    }
}
