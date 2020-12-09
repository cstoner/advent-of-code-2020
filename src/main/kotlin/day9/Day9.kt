package day9

import util.InputFileLoader

class WorkingSetWrapper(private val size: Int) {
    private val workingSet: MutableList<Long> = mutableListOf()
    private val workingSetSums: MutableMap<Long,MutableList<Long>> = mutableMapOf()

    fun add(n: Long) {
        if (workingSet.size == size) {
            val removedItem = workingSet.removeAt(0)
            workingSetSums.remove(removedItem)
        }
        for (key in workingSetSums.keys) {
            workingSetSums[key]!!.add(key + n)
        }
        workingSet.add(n)
        workingSetSums[n] = mutableListOf()
    }

    fun canBeMadeAsSum(n: Long): Boolean {
        val allSums: Set<Long> = workingSetSums.values.flatten().toSet()
        return allSums.contains(n)
    }
}

class Day9(private val input: List<Long>, private val workingSetSize: Int) {
    /**
     * Find the first number in our input that cannot be formed as a sum of the previous 25 (workingSetSize)
     */
    fun part1(): Long {
        val workingSet = WorkingSetWrapper(workingSetSize)
        input.subList(0, workingSetSize).forEach { workingSet.add(it) }
        input.subList(workingSetSize, input.size).forEach {
            if (workingSet.canBeMadeAsSum(it)) {
                workingSet.add(it)
            } else {
                return it
            }
        }

        return -1
    }

    /**
     * Take the ansewr from part 1, and find a continuous sequence of numbers that sum to it.
     *
     * Return the sum of the smallest and largest numbers in that sequence
     */
    fun part2(searchNum: Long): Long {
        var currIdx = 0
        var currentSum: Long = 0
        val numbersInSum = mutableListOf<Long>()
        while (currentSum != searchNum) {
            if (currentSum < searchNum) {
                numbersInSum.add(input[currIdx])
                currIdx += 1
            } else {
                numbersInSum.removeAt(0)
            }
            currentSum = numbersInSum.sum()
        }
        return numbersInSum.maxOrNull()!! + numbersInSum.minOrNull()!!
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day9.txt")
    val inputs = inputFileLoader.map { it.toLong() }
    val day9 = Day9(inputs, 25)

    val part1Answer = day9.part1()
    println("Part 1 - first number $part1Answer")
    println("Part 2 - sum of smallest/biggest : ${day9.part2(part1Answer)}")
}