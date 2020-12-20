package day18

/**
 * I think this parser needs to be a "right descent" parser, since we need to know the
 * precidence of the right term
 */
class Part2ExpressionParser(private val tokenizer: ExpressionTokenizer): ExpressionParser {
    var tokenizedInput: List<String> = listOf()
    var currentTokenIdx: Int = 0

    override fun parse(input: String): Expr {
        currentTokenIdx = 0
        tokenizedInput = tokenizer.tokenize(input)

        var expr = readSingleExpr()
        while( currentTokenIdx < tokenizedInput.size) {
            expr = readNextOperator(expr)
        }
        return expr
    }

    private fun readNextOperator(expr: Expr): Expr {
        var expr1 = expr
        val operator = readOperator()
        val rightExpr = readSingleExpr()

        // + has highest precedence, so bind the right side of the last argument to this instead
        if (operator == "+" && expr1 is InfixExpr) {
            val plusExpr = InfixExpr(expr1.rightExpr, "+", rightExpr)
            expr1 = InfixExpr(expr1.leftExpr, expr1.operator, plusExpr)
        } else {
            expr1 = InfixExpr(expr1, operator, rightExpr)
        }
        return expr1
    }

    fun readOperator(): String {
        check(isOperator(tokenizedInput[currentTokenIdx]))
        return tokenizedInput[currentTokenIdx++]
    }

    fun readNumeralExpr(): NumeralExpr {
        return NumeralExpr(tokenizedInput[currentTokenIdx++].toLong())
    }

    fun readParensExpr(): ParensExpr {
        check(tokenizedInput[currentTokenIdx] == "(")
        currentTokenIdx++
        var expr = readSingleExpr()
        while(isOperator(tokenizedInput[currentTokenIdx])) {
            expr = readNextOperator(expr)
        }
        check(tokenizedInput[currentTokenIdx] == ")")
        currentTokenIdx++
        return ParensExpr(expr)
    }

    fun readSingleExpr(): Expr {
        return if (tokenizedInput[currentTokenIdx] == "(") {
            readParensExpr()
        } else {
            readNumeralExpr()
        }
    }

    private fun isOperator(str: String): Boolean {
        return str.length == 1 && ExpressionTokenizer.isOperator(str[0])
    }
}