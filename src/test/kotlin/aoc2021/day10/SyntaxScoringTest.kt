package aoc2021.day10

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SyntaxScoringTest {

    @Test
    fun getSyntaxErrorScore() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day10.txt")

        val syntaxScoring = SyntaxScoring()

        assertEquals(315693, syntaxScoring.getSyntaxErrorScore(lines))
    }

    @Test
    fun getMiddleScore() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day10.txt")

        val syntaxScoring = SyntaxScoring()

        assertEquals(1870887234, syntaxScoring.getMiddleScore(lines))
    }
}