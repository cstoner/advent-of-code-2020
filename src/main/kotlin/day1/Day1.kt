package day1

import util.InputFileLoader

class Day1(private val input: List<Int>) {

    /**
     * Find the two numbers that sum to 2020
     */
    fun part1(): Pair<Int, Int>? {
        for( (i, value1) in input.withIndex()) {
            for ( value2 in input.slice(i until input.size)) {
                if (value1 + value2 == 2020) {
                    return Pair(value1, value2)
                }
            }
        }

        return null
    }

    /**
     * Find the three numbers that sum to 2020
     */
    fun part2(): Triple<Int, Int, Int>? {
        for( (i, value1) in input.withIndex()) {
            for ( (j, value2) in input.slice(i + 1 until input.size).withIndex()) {
                for ( value3 in input.slice(j + 1 until input.size))
                if (value1 + value2 + value3 == 2020) {
                    return Triple(value1, value2, value3)
                }
            }
        }

        return null

    }
}

fun main() {
    val inputLoader = InputFileLoader("/day1_input.txt")
    val input : List<Int> = inputLoader.asListOfInts()

    val day1 = Day1(input)
    val part1Solution = day1.part1()
    if (part1Solution != null) {
        print("The two numbers that sum to 2020 are (${part1Solution.first},  ${part1Solution.second}).\n")
        print("Their product is: ${part1Solution.first * part1Solution.second}\n\n")
    } else {
        print("No solution found to part 1")
    }

    val part2Solution = day1.part2()
    if (part2Solution != null) {
        print("The three numbers that sum to 2020 are (${part2Solution.first}, ${part2Solution.second}, ${part2Solution.third}).\n")
        print("Their product is: ${part2Solution.first * part2Solution.second * part2Solution.third}\n\n")
    } else {
        print("No solution found to part 2")
    }
}