package day17

import util.InputFileLoader
import java.lang.Integer.max
import java.lang.Integer.min

data class Point4d(val x: Int, val y: Int, val z: Int, val w: Int)

class Day17Part2(initialPoints: Set<Point4d>, val debug: Boolean =false) {
    var activePoints = initialPoints

    fun part2(): Int {
        print()
        repeat(6) { step() }
        return activePoints.size
    }

    fun step() {
        val nextGeneration: MutableSet<Point4d> = mutableSetOf()
        // Look at all currently active points
        activePoints.forEach { activePoint ->
            val activePointNeighbors = getNeighbors(activePoint)
            val activeNeighbors: Int = activePointNeighbors.count { activePoints.contains(it) }
            if (activeNeighbors in 2..3) {
                nextGeneration.add(activePoint)
            }
        }
        // Look at their neighbors
        val inactiveNeighbors = inactiveNeighborPoints()
        inactiveNeighbors.forEach { inactivePoint ->
            val inactivePointNeighbors = getNeighbors(inactivePoint)
            val activeNeighbors: Int = inactivePointNeighbors.count { activePoints.contains(it) }
            if (activeNeighbors == 3) {
                nextGeneration.add(inactivePoint)
            }
        }
        activePoints = nextGeneration
        print()
    }

    /**
     * Returns all points with active cells and their neighbors
     */
    private fun inactiveNeighborPoints(): Set<Point4d> {
        return activePoints.flatMap { getNeighbors(it) }
            .filter { !activePoints.contains(it) }
            .toSet()
    }

    private fun getNeighbors(point: Point4d): Set<Point4d> {
        val returnValue:MutableSet<Point4d> = mutableSetOf()
        for (i in -1..1) {
            for (j in -1..1) {
                for (k in -1..1) {
                    for (l in -1..1) {
                        returnValue.add(
                            Point4d(point.x + i, point.y + j, point.z + k, point.w + l)
                        )
                    }
                }
            }
        }
        // You are not your own neighbor
        returnValue.remove(point)
        return returnValue
    }

    private fun print() {
        if (!debug)
            return

        val ranges = getActivePointRanges()
        for (w in ranges.first.w..ranges.second.w) {
            for (z in ranges.first.z..ranges.second.z) {
                println("z=$z")
                for (y in ranges.first.y..ranges.second.y) {
                    for (x in ranges.first.x..ranges.second.x) {
                        if (Point4d(x, y, z, w) in activePoints) {
                            print('#')
                        } else {
                            print('.')
                        }
                    }
                    println()
                }
                println()
            }
        }
    }

    private fun getActivePointRanges(): Pair<Point4d, Point4d> {
        var maxPoint = Point4d(-10000,-10000,-10000, -10000)
        var minPoint = Point4d(10000,10000,10000, 10000)

        activePoints.forEach { point ->
            val minX = min(point.x, minPoint.x)
            val maxX = max(point.x, maxPoint.x)
            val minY = min(point.y, minPoint.y)
            val maxY = max(point.y, maxPoint.y)
            val minZ = min(point.z, minPoint.z)
            val maxZ = max(point.z, maxPoint.z)
            val minW = min(point.w, minPoint.w)
            val maxW = max(point.w, maxPoint.w)
            maxPoint = Point4d(maxX, maxY, maxZ, maxW)
            minPoint = Point4d(minX, minY, minZ, maxW)
        }

        return Pair(minPoint, maxPoint)
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day17.txt")
    val inputGrid = inputFileLoader.map { it }

    val activePoints: MutableSet<Point4d> = mutableSetOf()
    inputGrid.withIndex().forEach { row ->
        row.value.withIndex().forEach { col ->
            if (col.value == '#') {
                activePoints.add(Point4d(col.index, row.index, 0, 0))
            }
        }
    }

    val day17 = Day17Part2(activePoints)
    println("The number of active points after 6 steps is ${day17.part2()}")
}