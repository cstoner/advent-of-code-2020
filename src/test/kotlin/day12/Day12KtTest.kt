package day12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day12KtTest {
    @Test
    fun turnLeft() {
        assertEquals(WEST, turnLeft(NORTH))
        assertEquals(SOUTH, turnLeft(WEST))
        assertEquals(EAST, turnLeft(SOUTH))
        assertEquals(NORTH, turnLeft(EAST))
    }

    @Test
    fun turnRight() {
        assertEquals(EAST, turnRight(NORTH))
        assertEquals(SOUTH, turnRight(EAST))
        assertEquals(WEST, turnRight(SOUTH))
        assertEquals(NORTH, turnRight(WEST))
    }

    @Test
    fun part1() {
        val inputFileLoader = InputFileLoader("/day12_example.txt")
        val inputs = inputFileLoader.map { NavigationDirective(it.trim()) }
        val day12 = Day12(inputs)

        assertEquals(25, day12.part1())
    }

    @Test
    fun part2() {
        val inputFileLoader = InputFileLoader("/day12_example.txt")
        val inputs = inputFileLoader.map { NavigationDirective(it.trim()) }
        val day12 = Day12(inputs)

        assertEquals(286, day12.part2())
    }
}