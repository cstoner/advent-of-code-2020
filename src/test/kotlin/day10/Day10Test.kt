package day10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day10Test {
    @Test
    fun part1Example1() {
        val inputFileLoader = InputFileLoader("/day10_example1.txt")
        val rawInput = inputFileLoader.map { it.toInt() }
        val day10 = Day10(rawInput)

        assertEquals(35, day10.part1())
    }

    @Test
    fun part1Example2() {
        val inputFileLoader = InputFileLoader("/day10_example2.txt")
        val rawInput = inputFileLoader.map { it.toInt() }
        val day10 = Day10(rawInput)

        assertEquals(220, day10.part1())
    }

    @Test
    fun part2Example1() {
        val inputFileLoader = InputFileLoader("/day10_example1.txt")
        val rawInput = inputFileLoader.map { it.toInt() }
        val day10 = Day10(rawInput)

        assertEquals(8, day10.part2())
    }

    @Test
    fun part2Example2() {
        val inputFileLoader = InputFileLoader("/day10_example2.txt")
        val rawInput = inputFileLoader.map { it.toInt() }
        val day10 = Day10(rawInput)

        assertEquals(19208, day10.part2())
    }
}