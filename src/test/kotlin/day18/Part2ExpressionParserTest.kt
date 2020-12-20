package day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Part2ExpressionParserTest {
    val parser = Part2ExpressionParser(ExpressionTokenizer())

    @Test
    fun testSimpleExpression_addition() {
        val result = parser.parse("1 + 2")
        val expectedResult = InfixExpr(
            NumeralExpr(1),
            "+",
            NumeralExpr(2)
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun testSimpleExpression_multiplication() {
        val result = parser.parse("1 * 2")
        val expectedResult = InfixExpr(
            NumeralExpr(1),
            "*",
            NumeralExpr(2)
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun testSimpleExpression_correctPrec1() {
        val result = parser.parse("1 * 2 + 3")
        val expectedResult = InfixExpr(
            NumeralExpr(1),
            "*",
            InfixExpr(
                NumeralExpr(2),
                "+",
                NumeralExpr(3)
            )
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun testSimpleExpression_correctPrec2() {
        val result = parser.parse("1 * 2 + 3 * 4")
        val expectedResult = InfixExpr(
            InfixExpr(
                NumeralExpr(1),
                "*",
                InfixExpr(
                    NumeralExpr(2),
                    "+",
                    NumeralExpr(3)
                )
            ),
            "*",
            NumeralExpr(4)
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun example1() {
        val result = parser.parse("1 + (2 * 3) + (4 * (5 + 6))")
        assertEquals(51, result.eval())
    }

    @Test
    fun example2() {
        val result = parser.parse("2 * 3 + (4 * 5)")
        assertEquals(46, result.eval())
    }

    @Test
    fun example3() {
        val result = parser.parse("5 + (8 * 3 + 9 + 3 * 4 * 3)")
        assertEquals(1445, result.eval())
    }

    @Test
    fun example4() {
        val result = parser.parse("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
        assertEquals(669060, result.eval())
    }

    @Test
    fun example5() {
        val result = parser.parse("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")
        assertEquals(23340, result.eval())
    }
}