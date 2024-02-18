package com.example.calculator.generic.types;

import java.math.BigInteger;

public class MyBigInt implements MyType<BigInteger> {
    @Override
    public BigInteger parseConst(String s) {
        return new BigInteger(s);
    }
    @Override
    public BigInteger add(BigInteger left, BigInteger right) {
        return left.add(right);
    }
    @Override
    public BigInteger subtract(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }
    @Override
    public BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }
    @Override
    public BigInteger divide(BigInteger left, BigInteger right) {
        return left.divide(right);
    }
    @Override
    public BigInteger negate(BigInteger element) {
        return element.negate();
    }
    @Override
    public BigInteger fromInt(int x) {
        return BigInteger.valueOf(x);
    }
    @Override
    public BigInteger count(BigInteger x) {
        return BigInteger.valueOf(x.bitCount());
    }

    @Override
    public BigInteger max(BigInteger left, BigInteger right) {
        if (left.compareTo(right) > 0) {
            return left;
        } else {
            return right;
        }
    }

    @Override
    public BigInteger min(BigInteger left, BigInteger right) {
        if (left.compareTo(right) < 0) {
            return left;
        } else {
            return right;
        }
    }
}
