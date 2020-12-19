package day15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day15Test {

    @Test
    fun testStep() {
        val day15 = Day15(listOf(0,3,6))
        assertEquals(0, day15.step())
        assertEquals(3, day15.step())
        assertEquals(6, day15.step())
        assertEquals(0, day15.step())
        assertEquals(3, day15.step())
        assertEquals(3, day15.step())
        assertEquals(1, day15.step())
        assertEquals(0, day15.step())
        assertEquals(4, day15.step())
        assertEquals(0, day15.step())
    }

    @Test
    fun testPart1_example1() {
        val day15 = Day15(listOf(1,3,2))
        assertEquals(1, day15.part1())
    }

    @Test
    fun testPart1_example2() {
        val day15 = Day15(listOf(2,1,3))
        assertEquals(10, day15.part1())
    }

    @Test
    fun testPart1_example3() {
        val day15 = Day15(listOf(1,2,3))
        assertEquals(27, day15.part1())
    }

    @Test
    fun testPart1_example4() {
        val day15 = Day15(listOf(2,3,1))
        assertEquals(78, day15.part1())
    }

    @Test
    fun testPart1_example5() {
        val day15 = Day15(listOf(3,2,1))
        assertEquals(438, day15.part1())
    }

    @Test
    fun testPart1_example6() {
        val day15 = Day15(listOf(3,1,2))
        assertEquals(1836, day15.part1())
    }

}