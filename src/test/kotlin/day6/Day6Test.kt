package day6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day6Test {
    val qustionaires = readGroupQuestionsFromInput("/day6_example.txt")
    val day6 = Day6(qustionaires)

    @Test
    fun testPart1() {
        assertEquals(11, day6.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(6, day6.part2())
    }
}