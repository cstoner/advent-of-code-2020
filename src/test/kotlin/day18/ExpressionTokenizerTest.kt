package day18

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ExpressionTokenizerTest {
    private val tokenizer = ExpressionTokenizer()

    @Test
    fun testSimpleExpression() {
        val result = tokenizer.tokenize("1 + 2")
        assertEquals(listOf("1", "+", "2"), result)
    }

    @Test
    fun testMultipleDigits() {
        val result = tokenizer.tokenize("123 + 456")
        assertEquals(listOf("123", "+", "456"), result)
    }

    @Test
    fun testParens() {
        val result = tokenizer.tokenize("(123) + (456)")
        assertEquals(listOf("(", "123", ")", "+", "(", "456", ")"), result)
    }

    @Test
    fun testNestedParens() {
        val result = tokenizer.tokenize("((123) + 456)")
        assertEquals(listOf("(", "(", "123", ")", "+", "456", ")"), result)
    }

}