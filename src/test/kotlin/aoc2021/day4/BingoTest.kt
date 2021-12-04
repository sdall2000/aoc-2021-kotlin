package aoc2021.day4

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BingoTest {

    @Test
    fun playBingoSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day4Sample.txt")
        val bingo = Bingo()

        assertEquals(4512, bingo.playBingo(lines))
    }

    @Test
    fun playBingoSampleLastWinner() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day4Sample.txt")
        val bingo = Bingo()

        assertEquals(1924, bingo.playBingoLastWinner(lines))
    }

    @Test
    fun playBingo() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day4.txt")
        val bingo = Bingo()

        assertEquals(89001, bingo.playBingo(lines))
    }

    @Test
    fun playBingoLastWinner() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day4.txt")
        val bingo = Bingo()

        assertEquals(7296, bingo.playBingoLastWinner(lines))
    }
}