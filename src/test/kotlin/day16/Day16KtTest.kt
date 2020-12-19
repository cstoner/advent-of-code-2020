package day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day16KtTest {
    @Test
    fun testPart1() {
        val inputFileLoader = InputFileLoader("/day16_example.txt", false)
        val day16Input = Day16Input(inputFileLoader.map { it })
        val day16 = Day16(day16Input)

        assertEquals(71, day16.part1())
    }
}