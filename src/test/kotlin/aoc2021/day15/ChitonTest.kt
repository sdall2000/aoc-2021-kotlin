package aoc2021.day15

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ChitonTest {
    @Test
    fun lowestTotalRiskTestSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day15Sample.txt")

        val chiton = Chiton()

        assertEquals(40, chiton.lowestTotalRisk(lines))
    }

    @Test
    fun lowestTotalRiskTest() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day15.txt")

        val chiton = Chiton()

        // Not 421

        assertEquals(0, chiton.lowestTotalRisk(lines))
    }

    @Test
    fun lowestTotalRiskTestSmallSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day15SmallSample.txt")

        val chiton = Chiton()

        assertEquals(0, chiton.lowestTotalRisk(lines))
    }
}