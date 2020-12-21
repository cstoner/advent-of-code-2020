package day19

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.InputFileLoader

internal class Day19KtTest {
    @Test
    fun testPart1() {
        val ruleDefs = listOf("0: 4 1 5",
                "1: 2 3 | 3 2",
                "2: 4 4 | 5 5",
                "3: 4 5 | 5 4",
                "4: \"a\"",
                "5: \"b\"")
        val inputs = listOf(
                "ababbb",
                "bababa",
                "abbbab",
                "aaabbb",
                "aaaabbb")

        val rules: RuleSet = mutableMapOf()
        ruleDefs.forEach { parseRuleAndInsert(rules, it) }

        val day19 = Day19(rules, inputs)
        assertEquals(2, day19.part1())
    }

    @Test
    fun testPart2() {
        val inputFileLoader = InputFileLoader("/day19_part2.txt", false)
        val inputLines = inputFileLoader.map { it }
        val splitLocation = inputLines.indexOf("")

        val rules: RuleSet = mutableMapOf()
        inputLines.subList(0, splitLocation).forEach { parseRuleAndInsert(rules, it) }

        val inputs = inputLines.subList(splitLocation + 1, inputLines.size)

        val day19 = Day19(rules, inputs)

        assertNotNull(rules["42"]!!.match(rules, "babbb"))
        assertNotNull(rules["42"]!!.match(rules, "baabb"))
        assertNotNull(rules["42"]!!.match(rules, "bbbab"))
        assertNotNull(rules["42"]!!.match(rules, "bbbbb"))
        assertNotNull(rules["31"]!!.match(rules, "aabaa"))
        assertNotNull(rules["31"]!!.match(rules, "abaaa"))

//        assertEquals(3, day19.part1())
        assertEquals(12, day19.part2())
    }
}