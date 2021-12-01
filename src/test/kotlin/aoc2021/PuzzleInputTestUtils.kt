package aoc2021

import java.io.File
import java.net.URI
import java.net.URL

object PuzzleInputTestUtils {
    fun getPuzzleResourcePath(puzzleFileName: String): String {
        return "/puzzleInput/$puzzleFileName"
    }

    fun getPuzzleInputUrl(puzzleFileName: String): URL {
        return PuzzleInputTestUtils::class.java.getResource(getPuzzleResourcePath(puzzleFileName))!!
    }

    fun getPuzzleInputUri(puzzleFileName: String): URI {
        return getPuzzleInputUrl(puzzleFileName).toURI()
    }

    fun getPuzzleInputFile(puzzleFileName: String): File {
        return File(getPuzzleInputUri(puzzleFileName))
    }

    fun getPuzzleInputText(puzzleFileName: String): String {
        return getPuzzleInputFile(puzzleFileName).readText()
    }

    fun getPuzzleInputBlankLineDelimited(puzzleFileName: String): List<String> {
        return getPuzzleInputFile(puzzleFileName).readText().split("\n\n", "\r\n\r\n")
    }

    fun getPuzzleInputLines(puzzleFileName: String): List<String> {
        return getPuzzleInputFile(puzzleFileName).readLines()
    }

    fun getPuzzleInputLinesIntList(puzzleFileName: String): List<Int> {
        return getPuzzleInputLines(puzzleFileName)
            .map(String::toInt)
    }

    fun getPuzzleInputLinesIntArray(puzzleFileName: String): Array<Int> {
        return getPuzzleInputLinesIntList(puzzleFileName)
            .toTypedArray()
    }
}