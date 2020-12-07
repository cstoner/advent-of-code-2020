package day4

import util.InputFileLoader

typealias Passport = MutableMap<String, String>

/**
 * Utility function passed to {@link InputFileLoader.fold} to read the input file
 */
val passportInputLineReader = { acc: MutableList<Passport>, dataLine: String ->
    if (dataLine.isBlank()) {
        acc.add(mutableMapOf())
    } else {
        val currPassport: Passport = acc.last()
        val splitLine = dataLine.split("\\s+".toRegex())
        splitLine.forEach {
            val (key, value) = it.split(':')
            currPassport[key] = value
        }
    }

    acc
}

fun readPassports(inputFileLoader: InputFileLoader): List<Passport> {
    return inputFileLoader.fold(mutableListOf(mutableMapOf()), passportInputLineReader)
        .filter { it.isNotEmpty() }
}


class Day4(private val passports: List<Passport>) {
    val byrValidator = { byr: String ->
        byr.toInt() in 1920..2002
    }

    private val iyrValidator = { iyr: String ->
        iyr.toInt() in 2010..2020
    }

    private val eyrValidator = { eyr: String ->
        eyr.toInt() in 2020..2030
    }

    val hgtValidator = { hgt: String ->
        if (hgt.endsWith("cm")) {
            hgt.substring(0, hgt.length - 2).toInt() in 150..193
        } else if (hgt.endsWith("in")) {
            hgt.substring(0, hgt.length - 2).toInt() in 59..76
        } else {
            false
        }
    }

    val hclValidator = { hcl: String ->
        "#[0-9a-f]{6}".toRegex().matches(hcl)
    }

    val eclValidator = { ecl: String ->
        setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl)
    }

    val pidValidator = { hcl: String ->
        "[0-9]{9}".toRegex().matches(hcl)
    }

    private val validations: Map<String, (String) -> Boolean> = mapOf(
        Pair("byr", byrValidator),
        Pair("iyr", iyrValidator),
        Pair("eyr", eyrValidator),
        Pair("hgt", hgtValidator),
        Pair("hcl", hclValidator),
        Pair("ecl", eclValidator),
        Pair("pid", pidValidator)
    )

    /**
     * Count the number of valid passports
     */
    fun part1(): Int {
        return passports.filter { passportContainsValidFields(it) }.size;
    }

    private fun passportContainsValidFields(passport: Passport): Boolean {
        return validations.keys.all { passport.containsKey(it) }
    }

    /**
     * Run the validators and count number
     */
    fun part2(): Int {
        return passports.filter { passportFieldsPassValidation(it) }.size
    }

    private fun passportFieldsPassValidation(passport: Passport): Boolean {
        return validations.all {
            val key = it.key
            val validator = it.value
            val passportValue = passport[key] ?: return false

            validator.invoke(passportValue)
        }
    }
}

fun main() {
    val inputLoader = InputFileLoader("/day4_input.txt", false)
    val passports = readPassports(inputLoader)
    val day4 = Day4(passports)

    println("There are ${day4.part1()} passports with valid fields")
    println("There are ${day4.part2()} passports that pass validation")
}

