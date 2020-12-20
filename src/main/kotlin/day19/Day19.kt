package day19

import util.InputFileLoader

typealias RuleId = String
typealias RuleSet = MutableMap<RuleId, Rule>

interface Rule {
    // Returns the sequence of characters matched or null
    fun match(rules: RuleSet, ch: CharSequence): CharSequence?
}

class SequenceRule(val ruleIds: List<RuleId>) : Rule {
    override fun match(rules: RuleSet, ch: CharSequence): CharSequence? {
        var matchSoFar = ""
        var remainingString = ch
        ruleIds.forEach { id ->
            val ruleMatch = rules[id]?.match(rules, remainingString) ?: return null
            matchSoFar += ruleMatch
            remainingString = remainingString.subSequence(ruleMatch.length, remainingString.length)
        }
        return matchSoFar
    }
}

class OrRule(val rule1: SequenceRule, val rule2: SequenceRule) : Rule {
    override fun match(rules: RuleSet, ch: CharSequence): CharSequence? {
        val rule1Match = rule1.match(rules, ch)
        if (rule1Match != null)
            return rule1Match

        val rule2Match = rule2.match(rules, ch)
        if (rule2Match != null)
            return rule2Match

        return null
    }
}

class TerminalRule(val terminal: Char) : Rule {
    override fun match(rules: RuleSet, ch: CharSequence): CharSequence? {
        return if (ch.startsWith(terminal)) {
            terminal.toString()
        } else {
            null
        }
    }
}

class Day19(val rules: RuleSet, val inputs: List<String>) {

    fun part1(): Int {
        return inputs.filter { rules["0"]?.match(rules, it) == it }
            .count()
    }

    fun part2(): Int {
        // Replace rules with the following recursive rules
        // 8: 42 | 42 8
        //11: 42 31 | 42 11 31
        rules["8"] = OrRule(
            SequenceRule(listOf("42", "8")),
            SequenceRule(listOf("42"))
        )
        rules["11"] = OrRule(
            SequenceRule(listOf("42", "11", "31")),
            SequenceRule(listOf("42", "31")),
        )

        val results = inputs.filter { rules["0"]?.match(rules, it) == it }
        return results.count()
    }
}

fun parseRuleAndInsert(rules: RuleSet, input: String) {
    val (ruleId, rest) = input.split(": ")
    rules[ruleId] = when {
        rest.contains("|") -> {
            // example:
            // 116: 83 18 | 57 72
            val (left, right) = rest.split(" | ")
            val leftSequence = SequenceRule(left.split(" "))
            val rightSequence = SequenceRule(right.split(" "))
            OrRule(leftSequence, rightSequence)
        }
        rest.contains('"') -> {
            // example:
            // 83: "a"
            TerminalRule(rest[1])
        }
        else -> {
            // example:
            // 41: 57 83
            SequenceRule(rest.split(" "))
        }
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day19.txt", false)
    val inputLines = inputFileLoader.map { it }
    val splitLocation = inputLines.indexOf("")

    val rules: RuleSet = mutableMapOf()
    inputLines.subList(0, splitLocation).forEach { parseRuleAndInsert(rules, it) }

    val inputs = inputLines.subList(splitLocation + 1, inputLines.size)

    val day19 = Day19(rules, inputs)
    println("For part 1 ${day19.part1()} lines match the input rules")
    println("For part 2 ${day19.part2()} lines match the input rules")
}