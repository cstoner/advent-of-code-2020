package day11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day11Test {

    @Test
    fun part1step() {
        val inputFileLoader = InputFileLoader("/day11_example.txt")
        val inputGrid = inputFileLoader.map { it.toCharArray().toList() }
        val day11 = Day11(inputGrid)

        val firstStep = day11.part1Step()
        val expectedStep1 =
            "#.##.##.##\n" +
            "#######.##\n" +
            "#.#.#..#..\n" +
            "####.##.##\n" +
            "#.##.##.##\n" +
            "#.#####.##\n" +
            "..#.#.....\n" +
            "##########\n" +
            "#.######.#\n" +
            "#.#####.##"
        assertEquals(expectedStep1, day11.gridToString(firstStep))

        val secondStep = day11.part1Step()
        val expectedStep2 = "#.LL.L#.##\n" +
                "#LLLLLL.L#\n" +
                "L.L.L..L..\n" +
                "#LLL.LL.L#\n" +
                "#.LL.LL.LL\n" +
                "#.LLLL#.##\n" +
                "..L.L.....\n" +
                "#LLLLLLLL#\n" +
                "#.LLLLLL.L\n" +
                "#.#LLLL.##"
        assertEquals(expectedStep2, day11.gridToString(secondStep))

        val thirdStep = day11.part1Step()
        val expectedStep3 = "#.##.L#.##\n" +
                "#L###LL.L#\n" +
                "L.#.#..#..\n" +
                "#L##.##.L#\n" +
                "#.##.LL.LL\n" +
                "#.###L#.##\n" +
                "..#.#.....\n" +
                "#L######L#\n" +
                "#.LL###L.L\n" +
                "#.#L###.##"

        assertEquals(expectedStep3, day11.gridToString(thirdStep))
    }

    @Test
    fun testPart1() {
        val inputFileLoader = InputFileLoader("/day11_example.txt")
        val inputGrid = inputFileLoader.map { it.toCharArray().toList() }
        val day11 = Day11(inputGrid)

        assertEquals(37, day11.part1())
    }

    @Test
    fun testPart2Step() {
        val inputFileLoader = InputFileLoader("/day11_example.txt")
        val inputGrid = inputFileLoader.map { it.toCharArray().toList() }
        val day11 = Day11(inputGrid)

        val firstStep = day11.part2Step()
        val expectedStep1 =
            "#.##.##.##\n" +
            "#######.##\n" +
            "#.#.#..#..\n" +
            "####.##.##\n" +
            "#.##.##.##\n" +
            "#.#####.##\n" +
            "..#.#.....\n" +
            "##########\n" +
            "#.######.#\n" +
            "#.#####.##"
        assertEquals(expectedStep1, day11.gridToString(firstStep))

        val secondStep = day11.part2Step()
        val expectedStep2 =
            "#.LL.LL.L#\n" +
            "#LLLLLL.LL\n" +
            "L.L.L..L..\n" +
            "LLLL.LL.LL\n" +
            "L.LL.LL.LL\n" +
            "L.LLLLL.LL\n" +
            "..L.L.....\n" +
            "LLLLLLLLL#\n" +
            "#.LLLLLL.L\n" +
            "#.LLLLL.L#"
        assertEquals(expectedStep2, day11.gridToString(secondStep))

        val thirdStep = day11.part2Step()
        val expectedStep3 =
            "#.L#.##.L#\n" +
            "#L#####.LL\n" +
            "L.#.#..#..\n" +
            "##L#.##.##\n" +
            "#.##.#L.##\n" +
            "#.#####.#L\n" +
            "..#.#.....\n" +
            "LLL####LL#\n" +
            "#.L#####.L\n" +
            "#.L####.L#"

        assertEquals(expectedStep3, day11.gridToString(thirdStep))
    }
}