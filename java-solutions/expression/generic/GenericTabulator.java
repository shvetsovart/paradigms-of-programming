package com.example.calculator.generic;

import expression.generic.operations.TripleExpression;
import expression.generic.types.*;
import expression.generic.types.MyBigInt;
import expression.generic.types.MyDouble;
import expression.generic.types.MyInt;
import expression.generic.types.MyType;

import java.text.ParseException;

public class GenericTabulator implements Tabulator {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.printf("invalid input. need 2 arguments, found %s", args.length);
            throw new RuntimeException();
        }

        String mode = args[0].substring(1);
        String expression = args[1];

        Object[][][] result;
        result = new GenericTabulator().tabulate(mode, expression, -2, 2, -2, 2, -2, 2);

        for (int i = 0; i < 5; ++i)
            for (int j = 0; j < 5; ++j)
                for (int k = 0; k < 5; ++k)
                    System.out.printf("for x=%s y=%s z=%s: result=%s\n", i - 2, j - 2, k - 2, result[i][j][k]);
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return switch (mode) {
            case "i" -> calc(new MyInt(), expression, x1, x2, y1, y2, z1, z2);
            case "d" -> calc(new MyDouble(), expression, x1, x2, y1, y2, z1, z2);
            case "bi" -> calc(new MyBigInt(), expression, x1, x2, y1, y2, z1, z2);
            case "u" -> calc(new MyUncheckedInt(), expression, x1, x2, y1, y2, z1, z2);
            case "l" -> calc(new MyLong(), expression, x1, x2, y1, y2, z1, z2);
            case "s" -> calc(new MyShort(), expression, x1, x2, y1, y2, z1, z2);
            default -> null;
        };
    }

    private<T extends Number> Object[][][] calc(MyType<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        TripleExpression<T> parsedExpression = new GenericParser<T>().parse(expression);

        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i <= x2 - x1; ++i)
            for (int j = 0; j <= y2 - y1; ++j)
                for (int k = 0; k <= z2 - z1; ++k) {
                    try {
                        result[i][j][k] = parsedExpression.evaluate(type, type.fromInt(x1 + i), type.fromInt(y1 + j), type.fromInt(z1 + k));
                    } catch (Exception e) {
                        result[i][j][k] = null;
                    }
                }
        return result;
    }
}
