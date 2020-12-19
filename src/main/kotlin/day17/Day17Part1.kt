package day17

import util.InputFileLoader
import java.lang.Integer.max
import java.lang.Integer.min

data class Point3d(val x: Int, val y: Int, val z: Int)

class Day17Part1(initialPoints: Set<Point3d>, val debug: Boolean =false) {
    var activePoints = initialPoints

    fun part1(): Int {
        print()
        repeat(6) { step() }
        return activePoints.size
    }

    fun step() {
        val nextGeneration: MutableSet<Point3d> = mutableSetOf()
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
    private fun inactiveNeighborPoints(): Set<Point3d> {
        return activePoints.flatMap { getNeighbors(it) }
            .filter { !activePoints.contains(it) }
            .toSet()
    }

    private fun getNeighbors(point: Point3d): Set<Point3d> {
        val returnValue:MutableSet<Point3d> = mutableSetOf()
        for (i in -1..1) {
            for (j in -1..1) {
                for (k in -1..1) {
                    returnValue.add(Point3d(point.x + i, point.y + j, point.z + k))
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
        for (z in ranges.first.z..ranges.second.z) {
            println("z=$z")
            for (y in ranges.first.y..ranges.second.y) {
                for (x in ranges.first.x..ranges.second.x) {
                    if (Point3d(x, y, z) in activePoints) {
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

    private fun getActivePointRanges(): Pair<Point3d, Point3d> {
        var maxPoint = Point3d(-10000,-10000,-10000)
        var minPoint = Point3d(10000,10000,10000)

        activePoints.forEach { point ->
            val minX = min(point.x, minPoint.x)
            val maxX = max(point.x, maxPoint.x)
            val minY = min(point.y, minPoint.y)
            val maxY = max(point.y, maxPoint.y)
            val minZ = min(point.z, minPoint.z)
            val maxZ = max(point.z, maxPoint.z)
            maxPoint = Point3d(maxX, maxY, maxZ)
            minPoint = Point3d(minX, minY, minZ)
        }

        return Pair(minPoint, maxPoint)
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day17.txt")
    val inputGrid = inputFileLoader.map { it }

    val activePoints: MutableSet<Point3d> = mutableSetOf()
    inputGrid.withIndex().forEach { row ->
        row.value.withIndex().forEach { col ->
            if (col.value == '#') {
                activePoints.add(Point3d(col.index, row.index, 0))
            }
        }
    }

    val day17 = Day17Part1(activePoints)
    println("The number of active points after 6 steps is ${day17.part1()}")
}