package util

/**
 * Utility class to open a file from resources and map it to a more useful format
 */
class InputFileLoader {
    private val fileContents: List<String>

    constructor(fileName: String) {
        fileContents = InputFileLoader::class.java.getResource(fileName)
            .readText()
            .split('\n')
            .filter { it.isNotEmpty() }
    }

    fun asListOfInts(): List<Int> {
        return fileContents.map { it.toInt() }
    }
}