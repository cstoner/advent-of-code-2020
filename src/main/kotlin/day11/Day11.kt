package day11

import util.InputFileLoader

class Day11(private val initialGrid: List<List<Char>>) {
    val EMPTY_CHAIR = 'L'
    val FLOOR = '.'
    val OCCUPIED = '#'

    val gridHeight = initialGrid.size
    val gridWidth = initialGrid[0].size

    val chairLocations = findChairLocations(initialGrid)
    val gridIterations = mutableListOf(initialGrid)

    private fun findChairLocations(grid: List<List<Char>>): Set<Pair<Int, Int>> {
        val results = mutableListOf<Pair<Int, Int>>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == EMPTY_CHAIR) {
                    // Coordinates are in (x, y) format but we iterated in (column, row)
                    results.add(Pair(j, i))
                }
            }
        }
        return results.toSet()
    }

    private val neighborOffsets = listOf(
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, -1),
        // Pair(0, 0), - This pair is not included because you are not your own neighbor
        Pair(0, 1),
        Pair(1, -1),
        Pair(1, 0),
        Pair(1, 1),
    )

    fun gridToString(grid: List<List<Char>>): String {
        return grid.joinToString("\n") { it.joinToString("") }
    }

    fun reset() {
        gridIterations.clear()
        gridIterations.add(initialGrid)
    }

    /**
     * Given an (x, y) coordinate. Get the coordinates of all neighbors as (x,y) pairs), taking into consideration
     * boundaries of the grid
     */
    private fun getNeighborsForPart1(x: Int, y: Int): List<Pair<Int, Int>> {
        return neighborOffsets.map { Pair(x + it.first, y + it.second) }
            .filter { it.first in 0 until gridWidth }
            .filter { it.second in 0 until gridHeight }
    }

    /**
     * Takes the current grid, and iterates a step given the following rules:
     *   - If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
     *   - If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
     *   - Otherwise, the seat's state does not change.
     */
    fun part1Step(): List<List<Char>> {
        val currentGrid = gridIterations.last()
        // technically, we shouldn't populate with only FLOOR, but we pre-calculated the chair positions so this is fine
        val newGrid = MutableList(gridHeight) { MutableList(gridWidth) { FLOOR } }
        chairLocations.forEach { chairCoords ->
            // Grid is [column][row] but coords are [x][y], hence the reversal
            val thisChair = currentGrid[chairCoords.second][chairCoords.first]
            val neighbors = getNeighborsForPart1(chairCoords.first, chairCoords.second)
            val occupiedNeighbors = neighbors.map {
                when (currentGrid[it.second][it.first]) {
                    '#' -> 1
                    else -> 0
                }
            }.sum()

            newGrid[chairCoords.second][chairCoords.first] = thisChair
            if (thisChair == EMPTY_CHAIR && occupiedNeighbors == 0) {
                newGrid[chairCoords.second][chairCoords.first] = OCCUPIED
            } else if (thisChair == OCCUPIED && occupiedNeighbors >= 4) {
                newGrid[chairCoords.second][chairCoords.first] = EMPTY_CHAIR
            }
        }

        gridIterations.add(newGrid)
        return newGrid
    }

    /**
     * Iterate until the layout doesn't change and count the number of chairs
     */
    fun part1(): Int {
        while (true) {
            val currentGrid = gridToString(gridIterations.last())
            val newGrid = gridToString(part1Step())
            if (currentGrid == newGrid) {
                return currentGrid.toCharArray().count { it == OCCUPIED }
            }
        }
    }

    /**
     * Go in a specified direction from the origin until you find a chair or exit the grid
     *
     * @return the found chair or null
     */
    private fun findChairInDirection(origin: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int>? {
        var currLoc = Pair(origin.first + direction.first, origin.second + direction.second);

        while (currLoc.first in 0..gridWidth && currLoc.second in 0..gridHeight) {
            if (currLoc in chairLocations) {
                return currLoc
            }
            currLoc = Pair(currLoc.first + direction.first, currLoc.second + direction.second)
        }

        return null;
    }

    /**
     * Given a chair to ues as the origin, find all of the visible chairs it can see
     */
    private fun findVisibleChairsFromLocation(origin: Pair<Int, Int>): Set<Pair<Int, Int>> {
        return neighborOffsets.mapNotNull { findChairInDirection(origin, it) }
            .toSet()
    }

    /**
     * For each chair, find its visible neighbors in a lookup map
     */
    private val visibleChairMap = chairLocations.map { Pair(it, findVisibleChairsFromLocation(it)) }
        .toMap()

    /**
     * Takes the current grid, and iterates a step given the following rules:
     *   - If a seat is empty (L) and there are no occupied seats that it can "see", the seat becomes occupied.
     *   - If a seat is occupied (#) and _FIVE_ or more seats adjacent to it are also occupied, the seat becomes empty.
     *   - Otherwise, the seat's state does not change.
     */
    fun part2Step(): List<List<Char>> {
        val currentGrid = gridIterations.last()
        // technically, we shouldn't populate with only FLOOR, but we pre-calculated the chair positions so this is fine
        val newGrid = MutableList(gridHeight) { MutableList(gridWidth) { FLOOR } }
        chairLocations.forEach { chairCoords ->
            // Grid is [column][row] but coords are [x][y], hence the reversal
            val thisChair = currentGrid[chairCoords.second][chairCoords.first]
            val visibleNeighbors = visibleChairMap[chairCoords]
            val occupiedVisibleNeighbors = visibleNeighbors!!.map {
                when (currentGrid[it.second][it.first]) {
                    '#' -> 1
                    else -> 0
                }
            }.sum()

            newGrid[chairCoords.second][chairCoords.first] = thisChair
            if (thisChair == EMPTY_CHAIR && occupiedVisibleNeighbors == 0) {
                newGrid[chairCoords.second][chairCoords.first] = OCCUPIED
            } else if (thisChair == OCCUPIED && occupiedVisibleNeighbors >= 5) {
                newGrid[chairCoords.second][chairCoords.first] = EMPTY_CHAIR
            }
        }

        gridIterations.add(newGrid)
        return newGrid
    }

    /**
     * Iterate until the layout doesn't change and count the number of chairs
     */
    fun part2(): Int {
        while (true) {
            val currentGrid = gridToString(gridIterations.last())
            val newGrid = gridToString(part2Step())

            if (currentGrid == newGrid) {
                return currentGrid.toCharArray().count { it == OCCUPIED }
            }
        }
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day11.txt")
    val initialGrid = inputFileLoader.map { it.toCharArray().toList() }
    val day11 = Day11(initialGrid)

    println("Part 1 - After the grid settles, there are ${day11.part1()} occupied chairs")
    day11.reset()
    println("Part 2 - After the grid settles, there are ${day11.part2()} occupied chairs")
}