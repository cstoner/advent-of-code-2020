package day7

import util.InputFileLoader
import java.util.*

data class Bag(val adjective: String, val color: String)
data class BagWithCount(val count: Int, val bag: Bag)
data class ParsedInputSentence(val outerBag: Bag, val contents: List<BagWithCount>)

fun parseInputSentence(sentence: String): ParsedInputSentence {
    fun parseBagDescription(bagDescription: String): Bag {
        val (adjective: String, color: String, bagNoun: String) = bagDescription.trim().split(' ')
        check("bags?\\.?".toRegex().matches(bagNoun.trim()))

        return Bag(adjective.trim(), color.trim())
    }

    fun parseBagWithCountDescription(bagWithCountDescription: String): BagWithCount? {
        if (bagWithCountDescription.trim() == "no other bags.") {
            return null
        }
        val (count: String, adjective: String, color: String, bagNoun: String) = bagWithCountDescription.trim()
            .split(' ')
        check("bags?\\.?".toRegex().matches(bagNoun.trim()))

        return BagWithCount(count.toInt(), Bag(adjective.trim(), color.trim()))
    }

    val (container: String, contents: String) = sentence.split("contain")
    val containerBag = parseBagDescription(container)

    val splitContents = contents.split(',')
    val contentBagsWithNulls = splitContents.map { parseBagWithCountDescription(it) }.filterNotNull()

    return ParsedInputSentence(containerBag, contentBagsWithNulls)
}

class Day7(inputSentences: List<ParsedInputSentence>) {
    // A map of a bag to the types (and counts) of bags it contains
    private val forwardBagIndex: Map<Bag, List<BagWithCount>> =
        inputSentences.map { it.outerBag to it.contents }.toMap()

    // A reverse map of the types of bags that may contain a given bag
    private val reverseBagIndex: Map<Bag, MutableSet<Bag>> = inputSentences.fold(
        mutableMapOf(),
        { acc, parsedInputSentence ->
            // for each of the bag contents, add a link from it to the bag that contains it
            parsedInputSentence.contents.forEach { containerBagWithCount ->
                acc.getOrPut(containerBagWithCount.bag, { mutableSetOf() }).add(parsedInputSentence.outerBag)
            }
            acc
        })

    /**
     * Given a "shiny gold" bag, figure out how many outer bag colors could hold it
     */
    fun part1(): Int {
        val seenBags: MutableSet<Bag> = mutableSetOf()
        val bagQueue: Queue<Bag> = LinkedList(listOf(Bag("shiny", "gold")))

        while (bagQueue.isNotEmpty()) {
            val thisBag = bagQueue.remove()
            seenBags.add(thisBag)
            val outerBagsForThisBag = this.reverseBagIndex[thisBag]
            outerBagsForThisBag?.filter { !seenBags.contains(it) }?.forEach { bagQueue.add(it) }
        }

        return seenBags.size - 1 // -1 because "shiny gold" bag is in this list
    }

    /**
     * Now, we need to figure out how many bags our "shiny gold" bag must hold
     */
    fun part2(): Int {
        return getSubBags(Bag("shiny", "gold"))
    }

    // Map to hold memoized sub-bag counts
    private val subBagCountMap: MutableMap<Bag, Int> = mutableMapOf()

    // Memoized function to return the total number of sub-bags for a given bag
    private fun getSubBags(bag: Bag): Int {
        return subBagCountMap.getOrPut(bag, {
            val contents = forwardBagIndex[bag]!!
            // Add 1 to result for the container bag
            contents.map { it.count * (1 + getSubBags(it.bag)) }.sum()
        })
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day7.txt")
    val inputSentences = inputFileLoader.map { parseInputSentence(it) }
    val day7 = Day7(inputSentences)

    println("For part 1, there are ${day7.part1()} possible outer bags that could contain a 'shiny gold' bag")
    println("For part 2, shiny gold bags contain ${day7.part2()} bags")
}