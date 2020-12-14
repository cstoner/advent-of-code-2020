package day13

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader
import java.math.BigInteger

internal class Day13Test {
    @Test
    fun part1() {
        val inputFileLoader = InputFileLoader("/day13_example.txt")
        // The first value is the current time, and the second is a set of comma separated busIds (with 'x', that is currently unknown)
        val stringInput = inputFileLoader.map { it }
        val currentTime = stringInput[0].trim().toInt()
        val busIds = stringInput[1].trim().split(',')
        val day13 = Day13(currentTime, busIds)
        assertEquals(295, day13.part1())
    }

    @Test
    fun part2test1() {
        val day13 = Day13(0, "17,x,13,19".split(','))
        assertEquals(BigInteger.valueOf(3417L), day13.part2())
    }

    @Test
    fun part2test2() {
        val day13 = Day13(0, "67,7,59,61".split(','))
        assertEquals(BigInteger.valueOf(754018L), day13.part2())
    }

    @Test
    fun part2test3() {
        val day13 = Day13(0, "67,x,7,59,61".split(','))
        assertEquals(BigInteger.valueOf(779210L), day13.part2())
    }

    @Test
    fun part2test4() {
        val day13 = Day13(0, "67,7,x,59,61".split(','))
        assertEquals(BigInteger.valueOf(1261476L), day13.part2())
    }

    @Test
    fun part2test5() {
        val day13 = Day13(0, "1789,37,47,1889".split(','))
        assertEquals(BigInteger.valueOf(1202161486L), day13.part2())
    }

}