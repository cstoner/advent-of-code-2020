package day8

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day8Test {
    val inputFileLoader = InputFileLoader("/day8_example.txt")
    val instructions = inputFileLoader.map { parseInstruction(it) }
    val day8 = Day8(instructions)

    @Test
    fun part1() {
        assertEquals(5, day8.part1())
    }

    @Test
    fun part2() {
        assertEquals(8, day8.part2())
    }
}