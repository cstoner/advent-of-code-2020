package day3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day3Test {
    private val fileLoader = InputFileLoader("/day3_example.txt")
    private val input = fileLoader.map {it}
    private val day3 = Day3(input)

    @Test
    fun testPart1() {
        val day3ExampleResult = day3.part1()
        assertEquals(7, day3ExampleResult)
    }

    @Test
    fun testPart2() {
        val day3part2 = day3.part2()
        assertEquals(336, day3part2)
    }
}