package aoc2021.day20

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TrenchMapTest {

    @Test
    fun countLitPixels() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day20.txt")

        val trenchMap = TrenchMap()

        assertEquals(5268, trenchMap.countLitPixels(lines, 2))
    }

    @Test
    fun countLitPixels50() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day20.txt")

        val trenchMap = TrenchMap()

        assertEquals(16875, trenchMap.countLitPixels(lines, 50))
    }

    @Test
    fun countLitPixelsSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day20Sample.txt")

        val trenchMap = TrenchMap()

        assertEquals(35, trenchMap.countLitPixels(lines, 2))
    }
}