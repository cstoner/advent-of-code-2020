package day3

import util.InputFileLoader


data class Slope(
    val deltaX: Int,
    val deltaY: Int
)

class HillMap(
    private val input: List<String>
) {
    val TREE = '#'
    val EMPTY = '.'

    val width = input[0].length
    val height = input.size

    /**
     * Get the character at (x, y) with (0, 0) in the upper left corner and values increasing to the right and down
     */
    fun get(x: Int, y: Int): Char {
        check(x >= 0)
        check(y >= 0)

        // The map tiles to the right
        return input[y][x % width]
    }
}

class Day3(private val input: List<String>) {
    private val map = HillMap(input)

    /**
     * Returns the number of trees hit at a slope of (3, 1) (down 1, right 3)
     */
    fun part1(): Int {
        val part1Slope = Slope(3, 1)
        return calculateTreesHit(part1Slope)
    }

    /**
     * Given a handful of slopes, figure out what the product of the trees hit would be
     */
    fun part2(): Int {
        val slopes = listOf(
            Slope(1, 1),
            Slope(3, 1),
            Slope(5, 1),
            Slope(7, 1),
            Slope(1, 2)
        )

        val treesHit = slopes.map { calculateTreesHit(it) }
        return treesHit.reduce { acc: Int, item: Int -> acc * item }
    }

    private fun calculateTreesHit(part1Slope: Slope): Int {
        var currX = 0
        var currY = 0
        var treeCount = 0
        while (currY < map.height) {
            if (map.get(currX, currY) == map.TREE) {
                treeCount += 1
            }
            currX += part1Slope.deltaX
            currY += part1Slope.deltaY
        }
        return treeCount
    }
}

fun main() {
    val fileLoader = InputFileLoader("/day3_input.txt")
    val day3Input = fileLoader.map { it } // We just want raw strings here

    val day3 = Day3(day3Input)
    println("For part 1, we would hit ${day3.part1()} trees")
    println("For part 2, the product of trees hit would be ${day3.part2()}")
}