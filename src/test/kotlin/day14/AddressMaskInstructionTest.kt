package day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AddressMaskInstructionTest {
    @Test
    fun testAddressMaskConvertAddressToBinary_zero() {
        val zero = AddressMaskInstruction.convertAddressToBinary(0)
        assertEquals("0".repeat(36), zero)
    }

    @Test
    fun testAddressMaskConvertAddressToBinary_ten() {
        val ten = AddressMaskInstruction.convertAddressToBinary(10)
        assertEquals("0".repeat(32) + "1010", ten)
    }

    @Test
    fun testApplyMaskToAddress_simple() {
        val result = AddressMaskInstruction.applyMaskToAddress("0000", "1X10")
        assertEquals("1X10", result)
    }

    @Test
    fun testApplyMaskToAddress_allOnes() {
        val result = AddressMaskInstruction.applyMaskToAddress("1111", "1X10")
        assertEquals("1X11", result)
    }

    @Test
    fun testReplaceFloatingBitsWithSpecificValues_simple1() {
        val result = AddressMaskInstruction.replaceFloatingBitsWithSpecificValues("XXXX", "1111")
        assertEquals(15L, result)
    }

    @Test
    fun testReplaceFloatingBitsWithSpecificValues_simple2() {
        val result = AddressMaskInstruction.replaceFloatingBitsWithSpecificValues("XX0X", "111")
        assertEquals(13L, result)
    }
}