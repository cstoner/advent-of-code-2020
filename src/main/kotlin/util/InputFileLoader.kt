package util

/**
 * Utility class to open a file from resources and map it to a more useful format
 */
class InputFileLoader(fileName: String) {
    private val fileContents: List<String> = InputFileLoader::class.java.getResource(fileName)
        .readText()
        .split('\n')
        .filter { it.isNotEmpty() }

    fun asListOfInts(): List<Int> {
        return fileContents.map { it.toInt() }
    }

    fun <T>asListOf(lineMapper: (String) -> T): List<T> {
        return fileContents.map { lineMapper(it) }
    }
}