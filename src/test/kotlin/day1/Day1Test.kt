package day1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day1Test() {
    private val day1: Day1

    init {
        val inputLoader = InputFileLoader("/day1_example.txt")
        val input = inputLoader.map { it.toInt() }
        day1 = Day1(input)
    }

    @Test
    fun Day1Part1_works_for_example() {
        val part1Result = day1.part1()

        assertEquals(Pair(1721, 299), part1Result)
    }

    @Test
    fun Day1Part2_works_for_example() {
        val part2Result = day1.part2()

        assertEquals(Triple(979, 366, 675), part2Result)
    }
}