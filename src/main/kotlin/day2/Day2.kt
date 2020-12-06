package day2

import util.InputFileLoader

data class PasswordPolicy(
    val arg1: Int,
    val arg2: Int,
    val letter: Char
)


fun passwordPolicyLineMapper(line: String): Pair<PasswordPolicy, String> {
    // First half is password policy, second half is password
    val (policyString, password) = line.split(':')

    // Policy should be of form "A-B C" where:
    // A - minimum
    // B - maximum
    // C - letter
    val dashIndex = policyString.indexOf('-')
    val spaceIndex = policyString.indexOf(' ')
    // The character after the space should be the last character of the policy
    check(spaceIndex + 1 == policyString.length - 1)

    return Pair(
        PasswordPolicy(
            policyString.subSequence(0 until dashIndex).toString().toInt(),
            policyString.subSequence(dashIndex + 1 until spaceIndex).toString().toInt(),
            policyString[spaceIndex + 1]
        ),
        password.trim()
    )
}

class Day2(
    private val input: List<Pair<PasswordPolicy, String>>
) {

    /**
     * In this policy, arg1 and arg2 are the min and max number of times the char can appear in the password
     */
    private fun part1ValidPassword(policy: PasswordPolicy, password: String): Boolean {
        val letterCount = password.count { it == policy.letter }
        return (policy.arg1 <= letterCount) && (letterCount <= policy.arg2)
    }

    fun part1(): Int {
        return input.filter { part1ValidPassword(it.first, it.second) }
            .size
    }

    /**
     * In this policy, arg1 and arg2 are positions. _Exactly one_ of those needs to be the character.
     *
     * The arguments are not 0 indexed, so should have 1 subtracted
     */
    private fun part2ValidPassword(policy: PasswordPolicy, password: String): Boolean {
        return (password[policy.arg1 - 1] == policy.letter).xor(password[policy.arg2 - 1] == policy.letter)
    }

    fun part2(): Int {
        return input.filter { part2ValidPassword(it.first, it.second) }
            .size
    }
}

fun main() {
    val inputMapper = InputFileLoader("/day2_input.txt")
    val day2Inputs: List<Pair<PasswordPolicy, String>> = inputMapper.asListOf(::passwordPolicyLineMapper)
    val day2 = Day2(day2Inputs)

    val part1Count = day2.part1()
    println("For part 1, there are $part1Count valid passwords")
    val part2Count = day2.part2()
    println("For part 2, there are $part2Count valid passwords")
}