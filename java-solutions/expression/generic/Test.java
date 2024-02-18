package com.example.calculator.generic;
import com.example.calculator.generic.operations.TripleExpression;
import expression.generic.types.MyBigInt;
import expression.generic.types.MyDouble;
import expression.generic.types.MyShort;
import expression.generic.types.MyType;

import java.text.ParseException;

import static java.lang.Double.NaN;

public class Test {
    public static void main(String[] args) throws ParseException {
        System.out.println(Double.compare(10, NaN));
        System.out.println(-0.0 > NaN);
        String expression1 = "(((y + 164638224) - (x / y)) min ((894935891 + x) max (1606669139 - 1208852329)))";
        String expression2 = "(((y + 164638224) - (x / y)) max ((894935891 + x) max (1606669139 - 1208852329)))";
//        String expression = "x / y max 12415";
        expression.generic.types.MyDouble typeD = new expression.generic.types.MyDouble();
        expression.generic.types.MyShort typeS = new expression.generic.types.MyShort();
        test(expression1, typeD, typeD.fromInt(0), typeD.fromInt(0), typeD.fromInt(-6));
        test(expression2, typeD, typeD.fromInt(0), typeD.fromInt(0), typeD.fromInt(-6));
    }

    public static<T extends Number> void test(String expression, MyType<T> type, T x, T y, T z) throws ParseException {
        TripleExpression<T> expr = new GenericParser<T>().parse(expression);
        System.out.println(expr.evaluate(type, x, y, z));
    }
}
