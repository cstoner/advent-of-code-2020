package day8

import util.InputFileLoader

// Instruction set
val NOP = "nop"
val ACC = "acc"
val JMP = "jmp"

data class Instruction(val type: String, val value: Int)

fun parseInstruction(s: String): Instruction {
    val (cmd, value) = s.trim().split(' ')
    return Instruction(cmd, value.toInt())
}

class DuplicateInstructionExecutionException(message: String) : Exception(message)
class UnknownInstructionException(message: String) : Exception(message)

class Day8(private val instructions: List<Instruction>) {
    // "Program Counter" - The instruction at this position will be executed on the next cycle
    var pc = 0

    // Accumulator
    var acc = 0
    val executedInstructionIds: MutableSet<Int> = mutableSetOf()

    /**
     * Utility function used to reset the program state
     */
    private fun init() {
        pc = 0
        acc = 0
        executedInstructionIds.clear()
    }

    /**
     * Execute the current instruction
     */
    private fun execCmd(instruction: Instruction) {
        when (instruction.type) {
            NOP -> this.pc += 1
            ACC -> {
                this.acc += instruction.value
                this.pc += 1
            }
            JMP -> {
                this.pc += instruction.value
            }
            else -> {
                throw UnknownInstructionException("Encountered unknown instruction '${instruction.type}")
            }
        }
    }

    private fun exec(program: List<Instruction>) {
        while (this.pc < program.size) {
            if (executedInstructionIds.contains(this.pc)) {
                throw DuplicateInstructionExecutionException("Attempting to execute previously executed command at position ${this.pc}")
            } else {
                executedInstructionIds.add(this.pc)
                execCmd(program[this.pc])
            }
        }
        if (this.pc != program.size) {
            println("Program tried to execute instruction past end of inputs")
        }
    }

    /**
     * Part 1 is to figure out the accumulator value right before we execute an instruction for the 2nd time
     */
    fun part1(): Int {
        try {
            exec(instructions)
        } catch (e: Exception) {
            println("Caught exception: ${e.message}")
        }
        return this.acc
    }

    /**
     * Part 2 is to find the faulty instruction. Either a jmp was supposed to be a nop or vice-versa
     */
    fun part2(): Int {
        var currInstructionIdx = 0
        val mutableInstruction = instructions.toMutableList()
        while (currInstructionIdx < mutableInstruction.size) {
            val currInstruction = mutableInstruction[currInstructionIdx]
            // Swap the commands
            when (currInstruction.type) {
                JMP -> mutableInstruction[currInstructionIdx] = Instruction(NOP, currInstruction.value)
                NOP -> mutableInstruction[currInstructionIdx] = Instruction(JMP, currInstruction.value)
                ACC -> {
                    currInstructionIdx += 1
                    continue
                }
            }
            init()
            try {
                exec(mutableInstruction)
            } catch (e: DuplicateInstructionExecutionException) {
                mutableInstruction[currInstructionIdx] = currInstruction
                currInstructionIdx += 1
                continue
            } catch (e: Exception) {
                println("Encountered unexpected exception: ${e.message}")
                return -1
            }

            return this.acc
        }
        return -1
    }
}

fun main() {
    val inputFileLoader = InputFileLoader("/day8.txt")
    val instructions = inputFileLoader.map { parseInstruction(it) }
    val day8 = Day8(instructions)

    println("The accumulator value right before executing an instruction twice is ${day8.part1()}")
    println("The accumulator value with the corrupted instruction fixed is ${day8.part2()}")
}