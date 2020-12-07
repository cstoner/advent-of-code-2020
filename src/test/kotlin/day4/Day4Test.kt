package day4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day4Test {
    private val inputReader = InputFileLoader("/day4_example.txt", false)
    private val passports = readPassports(inputReader)
    private val day4 = Day4(passports)

    @Test
    fun testInputLoader() {
        // Just check that we read the right number
        assertEquals(4, passports.size)
    }

    @Test
    fun testPart1() {
        assertEquals(2, day4.part1())
    }

    @Test
    fun testValidators() {
        assertTrue(day4.byrValidator("2002"))
        assertFalse(day4.byrValidator("2003"))

        assertTrue(day4.hgtValidator("60in"))
        assertTrue(day4.hgtValidator("190cm"))
        assertFalse(day4.hgtValidator("190in"))
        assertFalse(day4.hgtValidator("190"))

        assertTrue(day4.hclValidator("#123abc"))
        assertFalse(day4.hclValidator("#123abz"))
        assertFalse(day4.hclValidator("123abc"))

        assertTrue(day4.eclValidator("brn"))
        assertFalse(day4.eclValidator("wat"))

        assertTrue(day4.pidValidator("000000001"))
        assertFalse(day4.pidValidator("0123456789"))
    }

    @Test
    fun testInvalidPassports() {
        val inputReader = InputFileLoader("/day4_invalid_passports.txt", false)
        val invalidPassports = readPassports(inputReader)
        val invalidDay4 = Day4(invalidPassports)

        assertEquals(0, invalidDay4.part2())
    }

    @Test
    fun testValidPassports() {
        val inputReader = InputFileLoader("/day4_valid_passports.txt", false)
        val validPassports = readPassports(inputReader)
        val validDay4 = Day4(validPassports)

        assertEquals(4, validDay4.part2())
    }
}