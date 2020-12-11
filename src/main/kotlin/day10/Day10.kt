package day10

import util.InputFileLoader

/**
 * We need to daisy chain our "joltage meters" (puzzle input) and calculate various attributes of them
 */
class Day10(rawInput: List<Int>) {
    // Add a zero for the "wall outlet"
    private val sortedJotageRatings = rawInput.plus(0).sorted()

    /**
     * Find the number of 1 jolt increases and 3 jolt increases. Answer is those two numbers multiplied together
     */
    fun part1(): Int {
        val pairs: List<Pair<Int, Int>> = sortedJotageRatings.subList(0, sortedJotageRatings.size - 1)
            .zip(sortedJotageRatings.subList(1, sortedJotageRatings.size))

        val joltDifferenceCount = pairs.fold(
            mutableMapOf(Pair(1, 0), Pair(3, 0)),
            { acc: MutableMap<Int, Int>, curr: Pair<Int, Int> ->
                acc.computeIfPresent(curr.second - curr.first) { _, v -> v + 1 }
                acc
            }
        )

        // The last hop is omitted, but assumed to be 3 greater than the max
        joltDifferenceCount.computeIfPresent(3) { _, i -> i + 1 }

        return joltDifferenceCount[1]!! * joltDifferenceCount[3]!!
    }

    /**
     * Figure out how many permutations there are that would be of the permutations
     */
    fun part2(): Long {
        val memoizedPermutations = MutableList<Long>(sortedJotageRatings.size) { 0 }
        memoizedPermutations[sortedJotageRatings.size - 1] = 1
        // Iterate through the list backwards, keeping track of how many permutations are available as we go
        for (i in sortedJotageRatings.size - 2 downTo 0) {
            val thisValue = sortedJotageRatings[i]
            // make a list of [i+1, i+2, i+3] as possible candidates for future adaptors
            val futureIndexCandidates = List(3) { it + i + 1 }
            val permutationsFromThisPoint = futureIndexCandidates.filter { it < memoizedPermutations.size }
                .filter { sortedJotageRatings[it] <= thisValue + 3 }
                .map { memoizedPermutations[it] }
                .sum()
            memoizedPermutations[i] = permutationsFromThisPoint
        }

        return memoizedPermutations[0]
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day10.txt")
    val rawInput = inputFileLoader.map { it.toInt() }
    val day10 = Day10(rawInput)

    println("Answer to part 1: ${day10.part1()}")
    println("Answer to part 2: ${day10.part2()}")
}