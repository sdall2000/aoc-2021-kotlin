package aoc2021

object Utils {
    fun splitByNewLine(text: String): List<String> {
        return text.trim().split("\n", "\r\n")
    }
}