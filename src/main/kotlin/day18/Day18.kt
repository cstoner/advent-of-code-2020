package day18

import util.InputFileLoader


class Day18(
    val part1Parser: Part1ExpressionParser,
    val part2Parser: Part2ExpressionParser,
    val input: List<String>) {

    fun part1(): Long {
        return input.map { part1Parser.parse(it).eval() }
            .sum()
    }

    fun part2(): Long {
        return input.map { part2Parser.parse(it).eval() }
            .sum()
    }
}

fun main() {
    val day1Parser = Part1ExpressionParser(ExpressionTokenizer())
    val day2Parser = Part2ExpressionParser(ExpressionTokenizer())

    val inputFilLoader = InputFileLoader("/day18.txt")
    val input = inputFilLoader.map { it }

    val day18 = Day18(day1Parser, day2Parser, input)

    println("The sum of all expressions from part 1 is: ${day18.part1()}")
    println("The sum of all expressions from part 2 is: ${day18.part2()}")
}