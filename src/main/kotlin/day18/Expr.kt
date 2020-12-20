package day18

data class ParensExpr(val subExpr: Expr): Expr {
    override fun eval(): Long {
        return subExpr.eval()
    }

    override fun toString(): String {
        return "($subExpr)"
    }
}

interface Expr {
    fun eval(): Long
    override fun toString(): String
}

data class InfixExpr(
    val leftExpr: Expr,
    val operator: String,
    val rightExpr: Expr
) : Expr {
    override fun eval(): Long {
        return when (operator) {
            "+" -> leftExpr.eval() + rightExpr.eval()
            "*" -> leftExpr.eval() * rightExpr.eval()
            else ->
                throw Error("Encountered unknown infix operator $operator")
        }
    }

    override fun toString(): String {
        return "$leftExpr $operator $rightExpr"
    }
}

data class NumeralExpr(val value: Long) : Expr {
    override fun eval(): Long {
        return value
    }

    override fun toString(): String {
        return "$value"
    }

}