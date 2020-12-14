package day13

import util.InputFileLoader
import java.math.BigInteger

data class BusWithOffset(val busId: BigInteger, val offset: BigInteger)

class Day13(
    private val initialTime: Int,
    private val busIds: List<String>
) {
    /**
     * Find the bus with the next departure, then do some arbitrary math for the answer
     */
    fun part1(): Int {
        val intOnlyBusIds: List<Int> = busIds.stream()
            .filter { it != "x" }
            .mapToInt { it.toInt() }
            .toArray()
            .toList()

        val busWithNextDeparture = intOnlyBusIds.map { Pair(it, nextArrival(it)) }
            .minByOrNull { it.second }!! // This will never be null

        return busWithNextDeparture.first * (busWithNextDeparture.second - initialTime)
    }

    private fun nextArrival(busId: Int): Int {
        val timeSinceLastArrival = initialTime.rem(busId)
        return initialTime - timeSinceLastArrival + busId
    }

    /**
     * Given the input, find a starting time that has the busses leaving sequentially.
     * 'x' values are "anything" and so no bus must leave
     *
     * This is essentially a LCM problem, but we have very large numbers to deal with
     */
    fun part2(): BigInteger {
        val bussesWithOffsets = busIds.withIndex()
            .filter { it.value != "x" }
            .map {
                BusWithOffset(
                    BigInteger.valueOf(it.value.toLong()),
                    BigInteger.valueOf(it.index.toLong())
                )
            }

        var currBusIdx = 1 // We initialize with bus 0, so start at 1
        var currentTimeMultiple: BigInteger = bussesWithOffsets[0].busId
        var currentTime: BigInteger = currentTimeMultiple // We ignore the initial time for this problem

        while (currBusIdx < bussesWithOffsets.size) {
            if (timeSlotFitsBusAndOffset(currentTime, bussesWithOffsets[currBusIdx])) {
                // We _should_ do a proper LCM, but all of our inputs are prime so it's really easy
                currentTimeMultiple *= bussesWithOffsets[currBusIdx].busId
                currBusIdx += 1
            } else {
                currentTime += currentTimeMultiple
            }
        }

        return currentTime
    }

    private fun timeSlotFitsBusAndOffset(time: BigInteger, busWithOffset: BusWithOffset): Boolean {
        return (time + busWithOffset.offset).rem(busWithOffset.busId) == BigInteger.ZERO
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day13.txt")
    // The first value is the current time, and the second is a set of comma separated busIds (with 'x', that is currently unknown)
    val rawStringInput = inputFileLoader.map { it }
    val currentTime = rawStringInput[0].trim().toInt()
    val busIds = rawStringInput[1].split(',')
    val day13 = Day13(currentTime, busIds)

    println("For part 1, the answer is ${day13.part1()}")
    println("For part 2, the answer is ${day13.part2()}")
}