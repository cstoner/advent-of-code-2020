package day18

class Part1ExpressionParser(private val tokenizer: ExpressionTokenizer): ExpressionParser {
    var tokenizedInput: List<String> = listOf()
    var currentTokenIdx: Int = 0

    override fun parse(input: String): Expr {
        currentTokenIdx = 0
        tokenizedInput = tokenizer.tokenize(input)

        var expr = readSingleExpr()
        while (currentTokenIdx < tokenizedInput.size) {
            val operator = readOperator()
            val rightExpr = readSingleExpr()
            expr = InfixExpr(expr, operator, rightExpr)
        }
        return expr
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
            val operator = readOperator()
            val rightExpr = readSingleExpr()

            expr = InfixExpr(expr, operator, rightExpr)
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
