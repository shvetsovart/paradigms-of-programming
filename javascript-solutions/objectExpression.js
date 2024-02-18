"use strict";

/**/

function Const(cnst) {
    this.cnst = cnst;
}
Const.prototype = {
    toString: function() {
        return this.cnst.toString();
    },
    evaluate: function() {
        return this.cnst;
    },
    prefix: function() {
        return this.cnst.toString();
    }
}

function Variable(varName) {
    this.varName = varName;
}
Variable.prototype = {
    toString: function() {
        return this.varName;
    },
    evaluate: function() {
        let varIndex = ['x', 'y', 'z'].indexOf(this.varName);
        return arguments[varIndex];
    },
    prefix: function() {
        return this.varName;
    }
}

/**/

function Operation(sign, operation, ...operands) {
    this.sign = sign;
    this.operation = operation;
    this.operands = operands;
}
Operation.prototype = {
    evaluate: function(x, y, z) {
        let args = [];
        for (let i = 0; i < this.operands.length; ++i)
            args[i] = this.operands[i].evaluate(x, y, z);
        return this.operation(...args);
    },
    toString: function() {
        return this.operands.join(' ') + " " + this.sign;
    },
    prefix: function() {
        return "(" + this.sign + " " + [...this.operands.map(x => x.prefix())].join(' ') + ")";
    }
}

function MakeOperation(sign, operation, ...operands) {
    Operation.call(this, sign, operation, ...operands);
}
MakeOperation.prototype = Object.create(Operation.prototype)

/**/

function Negate(...operands) {
    Operation.call(this, "negate", (a) => -a, ...operands);
}
Negate.prototype = Object.create(Operation.prototype);

//

function Sinh(...operands) {
    Operation.call(this, "sinh", (a) => Math.sinh(a), ...operands);
}
Sinh.prototype = Object.create(Operation.prototype);

//

function Cosh(...operands) {
    Operation.call(this, "cosh", (a) => Math.cosh(a), ...operands);
}
Cosh.prototype = Object.create(Operation.prototype);

//

function Add(...operands) {
    Operation.call(this, "+", (a, b) => a + b, ...operands);
}
Add.prototype = Object.create(Operation.prototype);

//

function Subtract(...operands) {
    Operation.call(this, "-", (a, b) => a - b, ...operands);
}
Subtract.prototype = Object.create(Operation.prototype);

//

function Multiply(...operands) {
    Operation.call(this, "*", (a, b) => a * b, ...operands);
}
Multiply.prototype = Object.create(Operation.prototype);

//

function Divide(...operands) {
    Operation.call(this, "/", (a, b) => a / b, ...operands);
}
Divide.prototype = Object.create(Operation.prototype);

//

let sum = function(...args) {
    let result = 0;
    for (const arg of args) {
        result += arg;
    }
    return result;
};

function Mean(...operands) {
    Operation.call(this, "mean", (...args) => (sum(...args) / (args.length)), ...operands);
}
Mean.prototype = Object.create(Operation.prototype);

//

let squareOperands = function(...args) {
    let res = []
    for (let i = 0; i < args.length; ++i) {
        res[i] = args[i] * args[i];
    }
    return res;
}

function Var(...operands) {
    let f = function(...args) {
        let mean = (sum(...args) / args.length);
        let meanSquared = (sum(...squareOperands(...args)) / args.length);
        return meanSquared - mean * mean;
    }

    Operation.call(this, "var", (...args) => f(...args), ...operands);
}
Var.prototype = Object.create(Operation.prototype);

/**/

function parse(expression) {
    let objects = expression.trim().split(/ +/);
    let stack = [];
    for (let i = 0; i < objects.length; ++i) {
        switch (objects[i]) {
            case 'x':
            case 'y':
            case 'z':
                stack.push(new Variable(objects[i]));
                break;
            case "sinh":
                stack.push(new Sinh(stack.pop()));
                break;
            case "cosh":
                stack.push(new Cosh(stack.pop()));
                break;
            case "negate":
                stack.push(new Negate(stack.pop()));
                break;
            case "+":
                let addSecondOperand = stack.pop()
                stack.push(new Add(stack.pop(), addSecondOperand));
                break;
            case "-":
                let subtractSecondOperand = stack.pop()
                stack.push(new Subtract(stack.pop(), subtractSecondOperand));
                break;
            case "*":
                let multiplySecondOperand = stack.pop()
                stack.push(new Multiply(stack.pop(), multiplySecondOperand));
                break;
            case "/":
                let divideSecondOperand = stack.pop()
                stack.push(new Divide(stack.pop(), divideSecondOperand));
                break;
            default:
                if (!isNaN(objects[i])) {
                    stack.push(new Const(Number(objects[i])));
                }
        }
    }
    return stack[0];
}

/**/

function ParseError(message) {
    this.name = "ParseError";
    this.message = message;
}
ParseError.prototype = Error.prototype;

let next;
let back;
let isEndOfExpression;

function addSpace(string, token) {
    return string.split(token).join(' ' + token + ' ');
}

function parsePrefix(expression) {
    // :NOTE: было бы короче через replace "(" на " ( "
    expression = addSpace(expression, '(');
    expression = addSpace(expression, ')');

    let pos = 0;
    let objects = expression.trim().split(/ +/);

    next = () => {
        if (pos === objects.length) {
            return "end-of-input";
        }
        return objects[pos++];
    }
    back = () => {
        pos--;
    }
    isEndOfExpression = () => {
        return pos < objects.length && objects[pos] === ")";
    }

    if (next() === '') {
        throw new ParseError("Empty expression");
    }
    back();

    let expr = parseExpression();
    if (next() !== "end-of-input") {
        throw new ParseError("Excessive info");
    }
    return expr;
}

function parseExpression() {
    let object = next();
    switch (object) {
        case '(':
            if (next() === ")") {
                throw new ParseError("Empty expression");
            }
            back();

            // :NOTE: слишком мало информации для пользователей в эксепшенах
            //        например, хотелось бы видеть ошибку на неверное количество операндов в операции
            let res = parseOperation();

            if (next() !== ")") {
                throw new ParseError("Expected closing bracket");
            }
            return res;
        case 'x':
        case 'y':
        case 'z':
            return new Variable(object);
    }
    if (!isNaN(object)) {
        return new Const(Number(object));
    }

    throw new ParseError("Unknown expression: " + object);
}

let expressionArguments = function() {
    let args = [];
    while (!isEndOfExpression()) {
        args.push(parseExpression());
    }
    return args;
}

function parseOperation() {
    let object = next();
    switch (object) {
        case "+":
            return new Add(parseExpression(), parseExpression());
        case "-":
            return new Subtract(parseExpression(), parseExpression());
        case "*":
            return new Multiply(parseExpression(), parseExpression());
        case "/":
            return new Divide(parseExpression(), parseExpression());
        case "negate":
            return new Negate(parseExpression());
        case "mean":
            return new Mean(...expressionArguments());
        case "var":
            return new Var(...expressionArguments());
        default:
            throw new ParseError("Unknown operation: " + object);
    }
}