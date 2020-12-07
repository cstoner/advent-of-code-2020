package day6

import util.InputFileLoader

// Represents all of the questions that a given group answered yes to and their count
typealias GroupAnswers = MutableMap<Char, Int>
data class SummarizedAnswers(var members: Int, val answers: GroupAnswers)

val questionaireLineReader = { acc: MutableList<SummarizedAnswers>, curr: String ->
    if (curr.isBlank()) {
        acc.add(SummarizedAnswers(0, mutableMapOf()))
    } else {
        val currentGroup = acc.last()
        currentGroup.members += 1
        curr.asIterable().forEach {
            currentGroup.answers.putIfAbsent(it, 0)
            currentGroup.answers.computeIfPresent(it) { _: Char, count: Int -> count + 1 }
        }
    }
    acc
}

fun readGroupQuestionsFromInput(filename: String): List<SummarizedAnswers> {
    val inputFileLoader = InputFileLoader(filename, false)
    return inputFileLoader.fold(mutableListOf(SummarizedAnswers(0, mutableMapOf())), questionaireLineReader)
        .filter { it.members > 0 }
}

class Day6( private val questionaires: List<SummarizedAnswers>) {

    /**
     * For part 1, we want to return the sum of the number of questions that each group answered yes to
     *
     * Most of our work is done reading the input into a Set, we just need to return the sum of their sizes
     */
    fun part1(): Int {
        return questionaires.map { it.answers.size }.sum()
    }

    /**
     * For part 2, we want to return only the number of questions that ALL members of the group answered yes to
     *
     * We can filter the map items down to just those where the count equals the size of the keySet
     */
    fun part2(): Int {
        return questionaires.map {
            it.answers.filterValues { questionCount: Int -> it.members == questionCount }.size
        }.sum()
    }
}

fun main() {
    val questionaires = readGroupQuestionsFromInput("/day6.txt")
    val day6 = Day6(questionaires)

    println("For part 1, the total number of 'group answered' questions is ${day6.part1()}")
    println("For part 2, the total number of questions every member of the group answered yes to is ${day6.part2()}")
}