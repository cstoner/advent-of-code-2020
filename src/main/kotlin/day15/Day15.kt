package day15

class Day15 (private val initialSequence: List<Int>) {
    private val seedSequence = initialSequence.toMutableList()
    // Keeps track of what turns the given number was spoken on
    private val sequenceMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    var turnNumber = 0
    private var lastNumberSpoken: Int = -1

    fun step(): Int {
        if (lastNumberSpoken != -1 && sequenceMap[lastNumberSpoken] == null) {
            sequenceMap[lastNumberSpoken] = mutableListOf(turnNumber)
        } else if (lastNumberSpoken != -1) {
            sequenceMap[lastNumberSpoken]!!.add(turnNumber)
        }

        turnNumber++

        return when {
            seedSequence.size > 0 -> {
                lastNumberSpoken = seedSequence.removeFirst()
                lastNumberSpoken
            }
            sequenceMap[lastNumberSpoken]!!.size == 1 -> {
                lastNumberSpoken = 0
                lastNumberSpoken
            }
            else -> {
                val allTimesSpoken = sequenceMap[lastNumberSpoken]!!
                lastNumberSpoken = allTimesSpoken[allTimesSpoken.size-1] - allTimesSpoken[allTimesSpoken.size-2]
                lastNumberSpoken
            }
        }
    }

    fun part1(): Int {
        repeat(2019) { step() }
        return step()
    }

    fun part2(): Int {
        repeat(29999999) { step() }
        return step()
    }

}

fun main() {
    val day15Part1 = Day15(listOf(0,13,1,8,6,15))
    println("Given the input, the 2020th number spoken is ${day15Part1.part1()}")
    val day15Part2 = Day15(listOf(0,13,1,8,6,15))
    println("Given the input, the 30000000th number spoken is ${day15Part2.part2()}")
}