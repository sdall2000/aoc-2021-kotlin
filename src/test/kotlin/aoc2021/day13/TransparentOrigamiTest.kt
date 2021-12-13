package aoc2021.day13

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TransparentOrigamiTest {

    @Test
    fun getVisibleDotsAfterFirstFold() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day13.txt")

        val transparentOrigami = TransparentOrigami()

        assertEquals(689, transparentOrigami.getVisibleDotsAfterFirstFold(lines))
    }

    @Test
    fun getVisibleDotsAfterAllFolds() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day13.txt")

        val transparentOrigami = TransparentOrigami()

        assertEquals(91, transparentOrigami.getVisibleDotsAfterAllFolds(lines))
    }
}