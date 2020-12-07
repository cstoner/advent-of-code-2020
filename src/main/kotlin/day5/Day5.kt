package day5

import util.InputFileLoader

class Day5(private val input: List<String>) {

    /**
     * Part 2 asks us to find a missing id from the middle of the list
     *
     * Easy enough to sort the list and check with brute force
     */
    fun part2(): Int {
        val binaryInputs = input.map { convertPassToId(it) }
        val sortedInputs = binaryInputs.sorted()

        sortedInputs.forEachIndexed { index, i ->
            if (sortedInputs[index + 1] != i + 1)
                return i + 1
        }

        return -1
    }

    fun part1(): Int? {
        val binaryInputs = input.map { convertPassToId(it) }
        return binaryInputs.maxOrNull()
    }

    /**
     * Part 1 wants us to split up the seat assignment as follows
     * AAAAAAASSS
     * Where A represents the aisle (either F or B) and SSS represents the chair seat (either R/L)
     *
     * The seat id is then the value of A*8 + S
     *
     * But that really is just a binary version of the ticket
     */
    fun convertPassToId(pass: String): Int {
        return pass.replace('F', '0')
            .replace('B', '1')
            .replace('R', '1')
            .replace('L', '0')
            .toInt(2)
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day5.txt")
    val inputs = inputFileLoader.map { it }
    val day5 = Day5(inputs)

    println("Maximum boarding pass id: ${day5.part1()}")
    println("Missing boarding pass id from middle: ${day5.part2()}")
}