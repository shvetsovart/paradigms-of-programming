"use strict";

function cnst(a) {
    return () => a;
}

function pi() {
    return Math.PI;
}

function e() {
    return Math.E;
}

function variable(varName) {
    let varIndex = ['x', 'y', 'z'].indexOf(varName);
    return (...args) => args[varIndex];
}

function unaryOperation(a, f) {
    return (x, y, z) => f(a(x, y, z));
}

function negate(a) {
    return unaryOperation(a, (a) => -a);
}

function sinh(a) {
    return unaryOperation(a, Math.sinh);
}

function cosh(a) {
    return unaryOperation(a, Math.cosh);
}

function binaryOperation(a, b, f) {
    return (x, y, z) => f(a(x, y, z), b(x, y, z));
}

function add(a, b) {
    return binaryOperation(a, b, (a, b) => a + b);
}

function subtract(a, b) {
    return binaryOperation(a, b, (a, b) => a - b);
}

function multiply(a, b) {
    return binaryOperation(a, b, (a, b) => a * b);
}

function divide(a, b) {
    return binaryOperation(a, b, (a, b) => a / b);
}