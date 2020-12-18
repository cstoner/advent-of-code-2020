package day14

import util.InputFileLoader

data class MemInstruction(val input: String) {
    val address = input.substring(4, input.indexOf(']')).toLong()
    val memValue = input.split('=')[1].trim().toLong()
}

data class ValueMaskInstruction(val input: String) {
    private val maskValue = input.split('=')[1].trim()

    val andMask = andMaskInit()

    // A mask of all 1s, except where there are 0s in the input mask
    private fun andMaskInit(): Long {
        return maskValue.replace('X', '1').toLong(2)
    }

    val orMask = orMaskInit()

    // A mask of all 0s, except where there are 1s in the input mask
    private fun orMaskInit(): Long {
        return maskValue.replace('X', '0').toLong(2)
    }
}

class AddressMaskInstruction(val input: String) {
    companion object {
        @JvmStatic
        fun convertAddressToBinary(address: Long): CharSequence {
            return convertMaskToBinary(address, 36)
        }

        @JvmStatic
        fun convertMaskToBinary(mask: Long, length: Int): CharSequence {
            return mask.toString(2).padStart(length, '0').substring(0, length)
        }

        @JvmStatic
        fun applyMaskToAddress(addressBinary: CharSequence, maskBinary: CharSequence): CharSequence {
            return maskBinary.zip(addressBinary) { maskChar, addressChar ->
                when (maskChar) {
                    'X' -> 'X'
                    '1' -> '1'
                    else -> addressChar
                }
            }.joinToString("")
        }

        @JvmStatic
        fun replaceFloatingBitsWithSpecificValues(maskedAddressBinary: CharSequence, values: CharSequence): Long {
            check(maskedAddressBinary.count { it == 'X' } == values.length)
            var currentValueIndex = 0
            return maskedAddressBinary.map {
                if (it == 'X') {
                    values[currentValueIndex++]
                } else {
                    it
                }
            }.joinToString("").toLong(2)
        }
    }

    private val maskValue = input.split('=')[1].trim()
    private val xCount = maskValue.count { it == 'X' }

    private val binaryFloatingBitRange = 0 until (1 shl xCount)
    private val expandedFloatingBits = binaryFloatingBitRange.map { convertMaskToBinary(it.toLong(), xCount) }

    fun apply(address: Long): List<Long> {
        val addressBinary = convertAddressToBinary(address)
        val mergedBinary = applyMaskToAddress(addressBinary, maskValue)

        return expandedFloatingBits.map { replaceFloatingBitsWithSpecificValues(mergedBinary, it) }
    }
}

class Day14(private val instructions: List<String>) {
    private val memory = mutableMapOf<Long, Long>()

    fun part1(): Long {
        // The first instruction is a mask instruction. Initialize here just to avoid NPE warnings
        var currentMask = ValueMaskInstruction(instructions[0])
        instructions.forEach { instruction ->
            if (instruction.startsWith("mem")) {
                val memInstruction = MemInstruction(instruction)
                memory[memInstruction.address] = applyMask(memInstruction.memValue, currentMask)
            } else if (instruction.startsWith("mask")) {
                currentMask = ValueMaskInstruction(instruction)
            }
        }

        return memory.values.sum()
    }

    fun part2(): Long {
        // The first instruction is a mask instruction. Initialize here just to avoid NPE warnings
        var currentMask = AddressMaskInstruction(instructions[0])

        instructions.forEach { instruction ->
            if (instruction.startsWith("mem")) {
                val memInstruction = MemInstruction(instruction)
                val addresses = currentMask.apply(memInstruction.address)
                addresses.forEach {
                    memory[it] = memInstruction.memValue
                }
            } else if (instruction.startsWith("mask")) {
                currentMask = AddressMaskInstruction(instruction)
            }
        }

        return memory.values.sum()
    }

    fun reset() {
        this.memory.clear()
    }

    private fun applyMask(number: Long, mask: ValueMaskInstruction): Long {
        return number and mask.andMask or mask.orMask
    }

}

fun main() {
    val inputFileLoader = InputFileLoader("/day14.txt")
    val inputs = inputFileLoader.map { it }
    val day14 = Day14(inputs)

    println("The sum of memory after part 1 is ${day14.part1()}")
    day14.reset()
    println("The sum of memory after part 2 is ${day14.part2()}")
}