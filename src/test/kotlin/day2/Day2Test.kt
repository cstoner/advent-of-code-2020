package day2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day2Test {
    @Test
    fun testInputMapper_simple() {
        val inputString = "1-2 c: password"
        val expectedOutput = Pair(PasswordPolicy(1, 2, 'c'), "password")

        val actualOutput = passwordPolicyLineMapper(inputString)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testInputMapper_larger_ints() {
        val inputString = "12-20 c: password"
        val expectedOutput = Pair(PasswordPolicy(12, 20, 'c'), "password")

        val actualOutput = passwordPolicyLineMapper(inputString)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun day2_part1() {
        val inputFileLoader = InputFileLoader("/day2_example.txt")
        val day2Input = inputFileLoader.map(::passwordPolicyLineMapper)
        val day2 = Day2(day2Input)

        assertEquals(2, day2.part1())
    }

    @Test
    fun day2_part2() {
        val inputFileLoader = InputFileLoader("/day2_example.txt")
        val day2Input = inputFileLoader.map(::passwordPolicyLineMapper)
        val day2 = Day2(day2Input)

        assertEquals(1, day2.part2())
    }
}