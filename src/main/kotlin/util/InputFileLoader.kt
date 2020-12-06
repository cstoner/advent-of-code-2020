package util

/**
 * Utility class to open a file from resources and map it to a more useful format
 */
class InputFileLoader(fileName: String, skipEmpty: Boolean = true) {
    private val fileContents: List<String> = InputFileLoader::class.java.getResource(fileName)
        .readText()
        .split('\n')
        .filter { it.isNotEmpty() || !skipEmpty }

    fun asListOfInts(): List<Int> {
        return map { it.toInt() }
    }

    fun <T>map(lineMapper: (String) -> T): List<T> {
        return fileContents.map { lineMapper(it) }
    }

    fun <T>fold(initial: T, operation: (T, String) -> T): T {
        return fileContents.fold(initial, operation)
    }
}