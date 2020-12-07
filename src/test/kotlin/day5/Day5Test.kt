package day5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5Test {
    private val day5 = Day5(listOf())

    @Test
    fun testConversion() {
        assertEquals(357, day5.convertPassToId("FBFBBFFRLR"))
        assertEquals(567, day5.convertPassToId("BFFFBBFRRR"))
        assertEquals(119, day5.convertPassToId("FFFBBBFRRR"))
        assertEquals(820, day5.convertPassToId("BBFFBBFRLL"))
    }
}