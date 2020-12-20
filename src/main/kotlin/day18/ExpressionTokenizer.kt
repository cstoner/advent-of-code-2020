package day18

class ExpressionTokenizer {
    var input = ""
    var currIdx = 0

    companion object {
        @JvmStatic
        fun isParen(ch: Char): Boolean {
            return ch in setOf('(', ')')
        }

        @JvmStatic
        fun isOperator(ch: Char): Boolean {
            return ch in setOf('*', '+')
        }

        @JvmStatic
        fun isWhitespace(ch: Char): Boolean {
            return ch == ' '
        }

        @JvmStatic
        fun isNumeral(ch: Char): Boolean {
            return ch in '0'..'9'
        }
    }

    fun tokenize(input: String): List<String> {
        this.input = input
        this.currIdx = 0

        val retval: MutableList<String> = mutableListOf()

        while(currIdx < input.length) {
            val currChar = input[currIdx]
            when {
                isWhitespace(currChar) -> {
                    currIdx++
                }
                isParen(currChar) -> {
                    retval.add(readParen())
                }
                isOperator(currChar) -> {
                    retval.add(readOperator())
                }
                isNumeral(currChar) -> {
                    retval.add(readNumeral())
                }
            }
        }

        return retval
    }

    private fun readParen(): String {
        check(isParen(input[currIdx]))
        return input[currIdx++].toString()
    }

    private fun readOperator(): String {
        check(isOperator(input[currIdx]))
        return input[currIdx++].toString()
    }

    private fun readNumeral(): String {
        check(isNumeral(input[currIdx]))
        val startIdx = currIdx
        while (currIdx < input.length && isNumeral(input[currIdx])) {
            currIdx++
        }
        return input.substring(startIdx, currIdx)
    }
}