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

class RecursiveRule8() : Rule {
    var count = 1

    override fun match(rules: RuleSet, ch: CharSequence): CharSequence? {
        var matchSoFar = ""
        var remainingString = ch
        repeat(count)  {
            val ruleMatch = rules["42"]?.match(rules, remainingString) ?: return null
            matchSoFar += ruleMatch
            remainingString = remainingString.subSequence(ruleMatch.length, remainingString.length)
        }
        return matchSoFar
    }

    fun iter() {
        count += 1
    }
}

class RecursiveRule11() : Rule {
    override fun match(rules: RuleSet, ch: CharSequence): CharSequence? {
        var matchSoFar = ""
        var loops = 0
        var rule42Match = rules["42"]?.match(rules, ch)
        while(rule42Match != null) {
            loops += 1
            matchSoFar += rule42Match
            rule42Match = rules["42"]?.match(rules, ch.subSequence(matchSoFar.length, ch.length))
        }

        if (loops == 0) {
            return null
        }

        var remainingString = ch.subSequence(matchSoFar.length, ch.length)
        repeat(loops)  {
            val ruleMatch = rules["31"]?.match(rules, remainingString) ?: return null
            matchSoFar += ruleMatch
            remainingString = remainingString.subSequence(ruleMatch.length, remainingString.length)
        }

        return matchSoFar
    }
}

// This just makes the "custom" logic a bit clearer
class RecursiveRule0() : Rule {
    override fun match(rules: RuleSet, ch: CharSequence): CharSequence? {
        val rule8 = RecursiveRule8()

        var rule8Match = rule8.match(rules, ch)
        while (rule8Match != null) {
            val remainingString = ch.substring(rule8Match.length, ch.length)
            val rule11 = RecursiveRule11()
            var rule11Match =  rule11.match(rules, remainingString)
            if ("$rule8Match$rule11Match" == ch) {
                return ch
            }
            rule8.iter()
            rule8Match = rule8.match(rules, ch)
        }

        return null
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
        // NOTE: Only rule 0 actually calls out to rule 8 or 11, and it does so in that order

        val rule0 = RecursiveRule0()
        val results = inputs.filter { rule0.match(rules, it) == it }

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