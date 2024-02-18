package com.example.calculator.generic.types;

import expression.DivideByZeroException;
import expression.OverflowException;

public class MyInt implements MyType<Integer> {

    @Override
    public Integer parseConst(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public Integer add(Integer left, Integer right) {
        if ((right >= 0) != (left + right >= left)) {
            throw new OverflowException(left + " + " + right);
        } else {
            return left + right;
        }
    }

    @Override
    public Integer subtract(Integer left, Integer right) {
        if ((right >= 0) != (left - right <= left)) {
            throw new OverflowException(left + " + " + right);
        } else {
            return left - right;
        }
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        if (left == Integer.MIN_VALUE && right == -1 ||
                left != 0 && right != 0 && (left * right) / right != left) {
            throw new OverflowException(left + " * " + right);
        } else {
            return left * right;
        }
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        if ((left == Integer.MIN_VALUE) && (right == -1)) {
            throw new OverflowException(left + " / " + right);
        }
        if (right == 0) {
            throw new DivideByZeroException(left + " " + right);
        }
        return left / right;
    }

    @Override
    public Integer negate(Integer element) {
        if (element == Integer.MIN_VALUE)
            throw new OverflowException("negatee overflow");
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
