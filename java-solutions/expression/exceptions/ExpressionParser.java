package com.example.calculator.exceptions;

import expression.*;
import expression.ParseExpressionException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isWhitespace;

import com.example.calculator.TripleExpression;

public class ExpressionParser implements TripleParser {
    public TripleExpression parse(String expression) {
        List<Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        return parseExpression(lexemeBuffer);
    }

    public enum LexemeType {
        NUMBER, VAR,
        LEFT_BRACKET, RIGHT_BRACKET,
        ADD, MINUS, BRACKET_MINUS,
        MULTIPLY, DIVIDE,
        EOF,
        L0, T0, BRACKET_T0, BRACKET_L0,
        MIN, MAX
    }

    private static List<Lexeme> lexAnalyze(final String expressionText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        int length = expressionText.length();

        int leftBracketCnt = 0, rightBracketCnt = 0;

        while (pos < expressionText.length()) {
            char c = expressionText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    leftBracketCnt++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    rightBracketCnt++;
                    if (rightBracketCnt > leftBracketCnt) {
                        throw new ParseExpressionException("expected more opening brackets");
                    }
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.ADD, c));
                    pos++;
                    continue;
                case '-':
                    if (pos == length - 1) {
                        throw new ParseExpressionException("expected something to get negative");
                    }
                    char next = expressionText.charAt(pos + 1);
                    boolean notInt = false;
                    if (next == '(') {
                        lexemes.add(new Lexeme(LexemeType.BRACKET_MINUS, c));
                        notInt = true;
                    } else if (isWhitespace(next) || next == '-' || next == 'x' || next == 'y' || next == 'z') {
                        lexemes.add(new Lexeme(LexemeType.MINUS, c));
                        notInt = true;
                    }
                    if (notInt) {
                        pos++;
                        continue;
                    }

                    try {
                        Integer.parseInt(takeNumber(expressionText, pos));
                    } catch (NumberFormatException e) {
                        throw new ParseExpressionException("Overflow number: " + takeNumber(expressionText, pos));
                    }
                    if ('0' <= next && next <= '9' && // MIN_VALUE PARSE
                            Integer.parseInt(takeNumber(expressionText, pos)) == Integer.MIN_VALUE) {
                        lexemes.add(new Lexeme(LexemeType.NUMBER, takeNumber(expressionText, pos)));
                        pos++;
                        while (pos < length && '0' <= expressionText.charAt(pos)
                                && expressionText.charAt(pos) <= '9') {
                            pos++;
                        }
                        pos--;
                    } else {
                        lexemes.add(new Lexeme(LexemeType.MINUS, c));
                    }
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.MULTIPLY, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.DIVIDE, c));
                    pos++;
                    continue;
                case 'x':
                case 'y':
                case 'z':
                    lexemes.add(new Lexeme(LexemeType.VAR, c));
                    pos++;
                    continue;
                default:
                    if ('0' <= c && c <= '9') { // NUMBER PARSE
                        String number = takeNumber(expressionText, pos);
                        while (pos < expressionText.length() && '0' <= expressionText.charAt(pos)
                                && expressionText.charAt(pos) <= '9') {
                            pos++;
                        }
                        lexemes.add(new Lexeme(LexemeType.NUMBER, number));
                    } else if (c == 't' || c == 'l') { // T0, L0 PARSE
                        StringBuilder sb = new StringBuilder();
                        if (length <= pos + 2) {
                            throw new ParseExpressionException("not completed t0 or l0 expression at pos " + pos);
                        }
                        char expZero = expressionText.charAt(pos + 1);
                        char expBracketOrSpace = expressionText.charAt(pos + 2);
                        sb.append(c + expZero + expBracketOrSpace);

                        if (expZero == '0' && (isWhitespace(expBracketOrSpace) || expBracketOrSpace == '(')) {
                            if (c == 'l') {
                                if (expBracketOrSpace == '(') {
                                    lexemes.add(new Lexeme(LexemeType.BRACKET_L0, sb.toString()));
                                } else {
                                    lexemes.add(new Lexeme(LexemeType.L0, sb.toString()));
                                }
                            } else {
                                if (expBracketOrSpace == '(') {
                                    lexemes.add(new Lexeme(LexemeType.BRACKET_T0, sb.toString()));
                                } else {
                                    lexemes.add(new Lexeme(LexemeType.T0, sb.toString()));
                                }
                            }
                        } else {
                            throw new ParseExpressionException(c + "0 parse exception: expected bracket or space, " +
                                    "found " + expBracketOrSpace);
                        }
                        pos += 2;
                    } else if (c == 'm') { // MIN, MAX PARSE
                        StringBuilder sb = new StringBuilder();

                        if (pos > 0 && !isWhitespace(expressionText.charAt(pos - 1)) &&
                                !(expressionText.charAt(pos - 1) == ')')) {
                            throw new ParseExpressionException("Required whitespace or right bracket" +
                                    " before min or max");
                        }

                        if (length <= pos + 3) {
                            throw new ParseExpressionException("Missing second min or max argument " + pos);
                        }
                        char expNextFirst = expressionText.charAt(pos + 1);
                        char expNextSecond = expressionText.charAt(pos + 2);
                        char expNextThird = expressionText.charAt(pos + 3);
                        sb.append(c + expNextFirst + expNextSecond);

                        if (expNextFirst == 'i' && expNextSecond == 'n' &&
                                (isWhitespace(expNextThird) || expNextThird == '(' || expNextThird == '-')) {
                            lexemes.add(new Lexeme(LexemeType.MIN, sb.toString()));
                        } else if (expNextFirst == 'a' && expNextSecond == 'x' &&
                                (isWhitespace(expNextThird) || expNextThird == '(' || expNextThird == '-')) {
                            lexemes.add(new Lexeme(LexemeType.MAX, sb.toString()));
                        } else {
                            if (!(expNextFirst == 'i' && expNextSecond == 'n') &&
                                    !(expNextFirst == 'a' && expNextSecond == 'x')) {
                                throw new ParseExpressionException("unknown command. did you mean 'min' | 'max'?");
                            }
                            throw new ParseExpressionException("Required whitespace or left bracket" +
                                    " after min or max");
                        }
                        pos += 3;
                    } else {
                        if (!isWhitespace(c)) {
                            if (c == ',') {
                                throw new ParseExpressionException("no comma expected");
                            }
                            throw new ParseExpressionException("Unknown character: " + c);
                        }
                        pos++;
                    }
            }
        }

        if (leftBracketCnt > rightBracketCnt) {
            throw new ParseExpressionException("expected more closing brackets");
        }

        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    private static String takeNumber(String expressionText, int pos) {
        char c = expressionText.charAt(pos);
        StringBuilder number = new StringBuilder();
        if (c == '-') {
            number.append(c);
            pos++;
            c = expressionText.charAt(pos);
        }
        do {
            number.append(c);
            pos++;
            if (pos >= expressionText.length()) {
                break;
            }
            c = expressionText.charAt(pos);
        } while (c >= '0' && c <= '9');
        return number.toString();
    }

    public static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }
    }

    public static class LexemeBuffer {
        private int pos;

        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    public static TripleExpression parseExpression(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return new Const(0);
        } else {
            lexemes.back();
            return parseAdd(lexemes);
        }
    }

    public static TripleExpression parseAdd(LexemeBuffer lexemes) {
        TripleExpression value = parseMultiply(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case ADD -> value = new CheckedAdd(value, parseMultiply(lexemes));
                case MINUS, BRACKET_MINUS -> value = new CheckedSubtract(value, parseMultiply(lexemes));
                case MIN -> value = new Min(value, parseExpression(lexemes));
                case MAX -> value = new Max(value, parseExpression(lexemes));
                case LEFT_BRACKET -> throw new ParseExpressionException("\"(\" at pos: " + lexemes.getPos());
                case NUMBER -> throw new ParseExpressionException("missing binary operation between numbers");
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    public static TripleExpression parseMultiply(LexemeBuffer lexemes) {
        TripleExpression value = parseFactor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case MULTIPLY -> value = new CheckedMultiply(value, parseFactor(lexemes));
                case DIVIDE -> value = new CheckedDivide(value, parseFactor(lexemes));
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    public static TripleExpression parseFactor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case BRACKET_L0:
                return new L0(parseFactor(lexemes), true);
            case BRACKET_T0:
                return new T0(parseFactor(lexemes), true);
            case L0:
                return new L0(parseFactor(lexemes), false);
            case T0:
                return new T0(parseFactor(lexemes), false);
            case BRACKET_MINUS:
                return new CheckedBracketNegate(parseFactor(lexemes));
            case MINUS:
                return new CheckedNegate(parseFactor(lexemes));
            case NUMBER:
                try {
                    return new Const(Integer.parseInt(lexeme.value));
                } catch (NumberFormatException e) {
                    throw new ParseExpressionException("Overflow number: " + lexeme.value);
                }
            case VAR:
                return new Variable(lexeme.value);
            case LEFT_BRACKET:
                TripleExpression value = parseExpression(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new ParseExpressionException("Unexpected token: " +
                            (lexeme.type == LexemeType.EOF ? "end of input" : lexeme.value));
                }
                return value;
            case MULTIPLY:
            case ADD:
            case DIVIDE:
                throw new ParseExpressionException("Missing first binary \"" + lexeme.value + "\" argument");
            case EOF:
            case RIGHT_BRACKET:
                lexemes.back();
                lexemes.back();
                lexeme = lexemes.next();
                throw new ParseExpressionException("Missing second binary \"" + lexeme.value + "\" argument");
            case MIN:
            case MAX:
                throw new ParseExpressionException("Missing first min or max argument");
            default:
                throw new ParseExpressionException("Unexpected token " + lexeme.value);
        }
    }
}
