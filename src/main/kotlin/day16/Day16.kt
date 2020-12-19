package day16

import util.InputFileLoader

typealias Ticket = List<Int>

data class Day16Input(val inputLines: List<String>) {
    val fields: Map<String, Pair<IntRange, IntRange>>
    val ourTicket: Ticket
    val otherTickets: List<Ticket>

    init {
        var inputLineIdx = 0

        val fieldMap: MutableMap<String, Pair<IntRange, IntRange>> = mutableMapOf()
        while (inputLines[inputLineIdx].trim() != "") {
            val parsedField = parseFieldLine(inputLines[inputLineIdx])
            fieldMap[parsedField.first] = parsedField.second
            inputLineIdx++
        }
        fields = fieldMap.toMap()

        inputLineIdx++
        check(inputLines[inputLineIdx++].trim() == "your ticket:")
        ourTicket = inputLines[inputLineIdx++].trim().split(",").map { it.toInt() }

        check(inputLines[inputLineIdx++].trim() == "")
        check(inputLines[inputLineIdx++].trim() == "nearby tickets:")

        val otherTicketsList = mutableListOf<Ticket>()
        while (inputLines[inputLineIdx].trim() != "" && inputLineIdx < inputLines.size) {
            val thisTicket = inputLines[inputLineIdx++].trim().split(",").map { it.toInt() }.toMutableList()
            otherTicketsList.add(thisTicket)
        }

        otherTickets = otherTicketsList.toList()
    }

    private fun parseFieldLine(line: String): Pair<String, Pair<IntRange, IntRange>> {
        val splitLine = line.split(": ")
        val ranges = splitLine[1].split(" or ")
        val range1 = ranges[0].split("-")
        val range2 = ranges[1].split("-")

        return Pair(
            splitLine[0],
            Pair(
                range1[0].toInt()..range1[1].toInt(),
                range2[0].toInt()..range2[1].toInt()
            )
        )
    }
}

class Day16(day16Input: Day16Input) {
    private val fields = day16Input.fields
    private val ourTicket = day16Input.ourTicket
    private val otherTickets = day16Input.otherTickets

    fun part1(): Int {
        return otherTickets.flatMap { findInvalidFields(it) }
            .sum()
    }

    fun part2(): Long {
        val cleanedTickets = otherTickets.filter { ticketIsValid(it) }
        val unidentifiedFields = fields.keys.toMutableSet()
        val unidentifiedFieldIndexes = ourTicket.indices.toMutableSet()

        val identifiedFields: MutableMap<String, Int> = mutableMapOf()

        while (unidentifiedFieldIndexes.isNotEmpty()) {
            for (i in unidentifiedFieldIndexes) {
                val unidentifiedFieldValues = getFieldFromAllTickets(cleanedTickets, i)
                val fieldCandidates: MutableList<String> = mutableListOf()
                for (fieldName in unidentifiedFields) {
                    val thisFieldRanges = fields[fieldName]!!
                    if (unidentifiedFieldValues.all { it in thisFieldRanges.first || it in thisFieldRanges.second }) {
                        fieldCandidates.add(fieldName)
                    }
                }
                if (fieldCandidates.size == 1) {
                    // found it!
                    identifiedFields[fieldCandidates[0]] = i
                    unidentifiedFields.remove(fieldCandidates[0])
                    unidentifiedFieldIndexes.remove(i)
                    break
                }
            }
        }

        val answerFields = fields.keys.filter { it.startsWith("departure") }
        val answerIndices = answerFields.map { identifiedFields[it]!! }

        return answerIndices.map { ourTicket[it].toLong() }
            .reduce { acc, curr -> acc*curr }
    }

    private fun findInvalidFields(ticket: Ticket): List<Int> {
        return ticket.filter { ! isValueValidForAnyField(it) }
    }

    private fun isValueValidForAnyField(value: Int): Boolean {
        return fields.values.any { value in it.first || value in it.second }
    }

    private fun ticketIsValid(ticket: Ticket): Boolean {
        return ticket.all { isValueValidForAnyField(it) }
    }

    private fun getFieldFromAllTickets(tickets: List<Ticket>, field: Int): List<Int> {
        return tickets.map { it[field] }
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day16.txt", false)
    val inputLines = inputFileLoader.map { it }
    val day16Input = Day16Input(inputLines)
    val day16 = Day16(day16Input)

    println("The sum of invalid fields is ${day16.part1()}")

    println("The product of our ticket's departure fields is ${day16.part2()}")

}