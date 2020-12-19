package day17

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17KtTest {
    @Test
    fun testStep() {
        val inputGrid = listOf(
            ".#.",
            "..#",
            "###"
        )

        val activePoints: MutableSet<Point3d> = mutableSetOf()
        inputGrid.withIndex().forEach { row ->
            row.value.withIndex().forEach { col ->
                if (col.value == '#') {
                    activePoints.add(Point3d(col.index, row.index, 0))
                }
            }
        }

        val day17 = Day17Part1(activePoints)
        day17.step()
        assertEquals(11, day17.activePoints.size)
        day17.step()
        assertEquals(21, day17.activePoints.size)
        day17.step()
        assertEquals(38, day17.activePoints.size)
        day17.step()
        day17.step()
        day17.step()
        assertEquals(112, day17.activePoints.size)
    }
}