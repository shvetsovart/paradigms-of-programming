package com.example.calculator.generic;

import expression.ParseExpressionException;
import expression.generic.operations.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isWhitespace;

public class GenericParser<T extends Number> {
    public TripleExpression<T> parse(String expression) throws ParseException {
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
        COUNT,
        MIN, MAX
    

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
                    } else if (c == 'c') { // COUNT PARSE
                        char expNextFirst = expressionText.charAt(pos + 1);
                        char expNextSecond = expressionText.charAt(pos + 2);
                        char expNextThird = expressionText.charAt(pos + 3);
                        char expNextFourth = expressionText.charAt(pos + 4);
                        StringBuilder sb = new StringBuilder();
                        sb.append(c + expNextFirst + expNextSecond + expNextThird + expNextFourth);
                        if (expNextFirst == 'o' && expNextSecond == 'u' && expNextThird == 'n' &&
                                                                            expNextFourth == 't') {
                            lexemes.add(new Lexeme(LexemeType.COUNT, sb.toString()));
                        }
                        pos += 5;
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
        } while (c >= '0' && c <= '9' || c == '.');
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

    public TripleExpression<T> parseExpression(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return new Const<>("0");
        } else {
            lexemes.back();
            return parseAdd(lexemes);
        }
    }

    public TripleExpression<T> parseAdd(LexemeBuffer lexemes) {
        TripleExpression<T> value = parseMultiply(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case ADD -> value = new Add<>(value, parseMultiply(lexemes));
                case MINUS, BRACKET_MINUS -> value = new Subtract<>(value, parseMultiply(lexemes));
                case MIN -> value = new Min<>(value, parseExpression(lexemes));
                case MAX -> value = new Max<>(value, parseExpression(lexemes));
                case LEFT_BRACKET -> throw new ParseExpressionException("\"(\" at pos: " + lexemes.getPos());
                case NUMBER -> throw new ParseExpressionException("missing binary operation between numbers");
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    public TripleExpression<T> parseMultiply(LexemeBuffer lexemes) {
        TripleExpression<T> value = parseFactor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case MULTIPLY -> value = new Multiply<>(value, parseFactor(lexemes));
                case DIVIDE -> value = new Divide<>(value, parseFactor(lexemes));
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    public TripleExpression<T> parseFactor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case COUNT:
                return new Count<>(parseFactor(lexemes));
            case MINUS:
            case BRACKET_MINUS:
                return new Negate<>(parseFactor(lexemes));
            case NUMBER:
                try {
                    return new Const<>(lexeme.value);
                } catch (NumberFormatException e) {
                    throw new ParseExpressionException("Overflow number: " + lexeme.value);
                }
            case VAR:
                return new Variable<>(lexeme.value);
            case LEFT_BRACKET:
                TripleExpression<T> value = parseExpression(lexemes);
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
