package day18

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Part1ExpressionParserTest {
    val parser = Part1ExpressionParser(ExpressionTokenizer())

    @Test
    fun testSimpleAddition() {
        val result = parser.parse("1 + 2")

        assertEquals(InfixExpr(NumeralExpr(1), "+", NumeralExpr(2)), result)
        assertEquals(3, result.eval())
    }

    @Test
    fun testChainedAddition() {
        val result = parser.parse("1 + 2 + 3")

        val expectedResult = InfixExpr(
            InfixExpr(
                NumeralExpr(1),
                "+",
                NumeralExpr(2)
            ),
            "+",
            NumeralExpr(3)
        )
        assertEquals(expectedResult, result)
        assertEquals(6, result.eval())
    }

    @Test
    fun testSimpleMultiplication() {
        val result = parser.parse("1 * 2")

        val expectedResult = InfixExpr(
            NumeralExpr(1),
            "*",
            NumeralExpr(2),
        )
        assertEquals(expectedResult, result)
        assertEquals(2, result.eval())
    }

    @Test
    fun testChainedMultiplication() {
        val result = parser.parse("1 * 2 * 3")

        val expectedResult = InfixExpr(
            InfixExpr(
                NumeralExpr(1),
                "*",
                NumeralExpr(2)
            ),
            "*",
            NumeralExpr(3)
        )
        assertEquals(expectedResult, result)
        assertEquals(6, result.eval())
    }

    @Test
    fun checkCorrectOperatorPrecedence() {
        val result = parser.parse("1 + 2 * 3")

        assertEquals(9, result.eval())
    }

    @Test
    fun checkParens() {
        val result = parser.parse("1 + (2 * 3)")
        val expectedResult = InfixExpr(
            NumeralExpr(1),
            "+",
            ParensExpr(
                InfixExpr(
                    NumeralExpr(2),
                    "*",
                    NumeralExpr(3)
                )
            )
        )
        assertEquals(expectedResult, result)
        assertEquals(7, result.eval())
    }

    @Test
    fun example1() {
        val result = parser.parse("2 * 3 + (4 * 5)")
        assertEquals(26, result.eval())
    }

    @Test
    fun example2() {
        val result = parser.parse("5 + (8 * 3 + 9 + 3 * 4 * 3)")
        assertEquals(437, result.eval())
    }

    @Test
    fun example3() {
        val result = parser.parse("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
        assertEquals(12240, result.eval())
    }

    @Test
    fun example4() {
        val result = parser.parse("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")
        assertEquals(13632, result.eval())
    }
}