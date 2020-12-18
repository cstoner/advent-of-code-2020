package day14

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ValueMaskInstructionTest {
    @Test
    fun testMaskInstruction() {
        val instruction = ValueMaskInstruction("mask = 1001X000XX00011100X100X1100XX11000X0")
        assertEquals("100110001100011100110011100111100010".toLong(2), instruction.andMask)
        assertEquals("100100000000011100010001100001100000".toLong(2), instruction.orMask)
    }

    @Test
    fun exampleProgram1() {
        val inputs = listOf(
            "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
            "mem[8] = 11",
            "mem[7] = 101",
            "mem[8] = 0")
        val day14 = Day14(inputs)
        assertEquals(165, day14.part1())
    }
}