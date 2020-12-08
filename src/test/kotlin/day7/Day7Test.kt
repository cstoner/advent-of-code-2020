package day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day7Test {
    val inputFileLoader = InputFileLoader("/day7_example.txt")
    val inputSentences = inputFileLoader.map { parseInputSentence(it) }
    val day7 = Day7(inputSentences)

    @Test
    fun testPart1() {
        assertEquals(4, day7.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(32, day7.part2())
    }
}