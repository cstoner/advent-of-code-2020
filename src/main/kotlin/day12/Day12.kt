package day12

import util.InputFileLoader
import kotlin.math.abs

data class NavigationDirective(val inputString: String) {
    val direction = inputString[0]
    val count = inputString.substring(1, inputString.length).toInt()
}

val NORTH = Pair(0, 1)
val SOUTH = Pair(0, -1)
val EAST = Pair(1, 0)
val WEST = Pair(-1, 0)

fun turnLeft(currentDirection: Pair<Int, Int>): Pair<Int, Int> {
    // 90 degree left rotation matrix
    // | 0  -1 |
    // | 1   0 |
    return Pair(
        currentDirection.second * -1,
        currentDirection.first
    )
}

fun turnRight(currentDirection: Pair<Int, Int>): Pair<Int, Int> {
    // 90 degree left rotation matrix
    // |  0   1 |
    // | -1   0 |
    return Pair(
        currentDirection.second,
        currentDirection.first * -1
    )
}

class Day12(private val navigationDirectives: List<NavigationDirective>) {
    private var shipPosition = Pair(0, 0)
    private var shipHeading = EAST
    private var waypointPosition = Pair(10, 1)

    fun part1(): Int {
        navigationDirectives.forEach { directive ->
            applyDirectiveToShip(directive)
        }

        return abs(shipPosition.first) + abs(shipPosition.second)
    }

    fun part2(): Int {
        navigationDirectives.forEach { directive ->
            applyWaypointDirectives(directive)
        }

        return abs(shipPosition.first) + abs(shipPosition.second)
    }


    fun reset() {
        shipPosition = Pair(0, 0)
    }

    private fun applyDirectiveToShip(directive: NavigationDirective) {
        when (directive.direction) {
            'N' -> shipPosition = Pair(shipPosition.first, shipPosition.second + directive.count)
            'S' -> shipPosition = Pair(shipPosition.first, shipPosition.second - directive.count)
            'E' -> shipPosition = Pair(shipPosition.first + directive.count, shipPosition.second)
            'W' -> shipPosition = Pair(shipPosition.first - directive.count, shipPosition.second)
            'R' -> {
                val times = directive.count / 90
                repeat(times) { shipHeading = turnRight(shipHeading) }
            }
            'L' -> {
                val times = directive.count / 90
                repeat(times) { shipHeading = turnLeft(shipHeading) }
            }
            'F' -> shipPosition = Pair(
                shipPosition.first + shipHeading.first * directive.count,
                shipPosition.second + shipHeading.second * directive.count
            )
            else -> throw Error("Unknown directive '${directive.direction}'")
        }
    }

    private fun applyWaypointDirectives(directive: NavigationDirective) {
        when (directive.direction) {
            'N' -> waypointPosition = Pair(waypointPosition.first, waypointPosition.second + directive.count)
            'S' -> waypointPosition = Pair(waypointPosition.first, waypointPosition.second - directive.count)
            'E' -> waypointPosition = Pair(waypointPosition.first + directive.count, waypointPosition.second)
            'W' -> waypointPosition = Pair(waypointPosition.first - directive.count, waypointPosition.second)
            'R' -> {
                val times = directive.count / 90
                repeat(times) { waypointPosition = turnRight(waypointPosition) }
            }
            'L' -> {
                val times = directive.count / 90
                repeat(times) { waypointPosition = turnLeft(waypointPosition) }
            }
            'F' -> {
                shipPosition = Pair(
                    shipPosition.first + (waypointPosition.first * directive.count),
                    shipPosition.second + (waypointPosition.second * directive.count)
                )
            }
            else -> throw Error("Unknown directive '${directive.direction}'")
        }
    }

}


fun main() {
    val inputFileLoader = InputFileLoader("/day12.txt")
    val navigationDirectives = inputFileLoader.map { NavigationDirective(it.trim()) }
    val day12 = Day12(navigationDirectives)

    println("After traveling the specified directions, we are ${day12.part1()} units away (manhattan distance)")
    day12.reset()
    println("After following the waypoint directives, we are ${day12.part2()} units away (manhattan distance)")

}