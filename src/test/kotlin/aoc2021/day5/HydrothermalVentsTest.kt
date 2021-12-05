package aoc2021.day5

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HydrothermalVentsTest {

    @Test
    fun countPointsWithOverlappingLinesSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day5Sample.txt")
        val hydrothermalVents = HydrothermalVents()

        assertEquals(5, hydrothermalVents.countPointsWithOverlappingLines(lines, 2))
    }

    @Test
    fun countPointsWithOverlappingAllLinesSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day5Sample.txt")
        val hydrothermalVents = HydrothermalVents()

        assertEquals(12, hydrothermalVents.countPointsWithOverlappingAllLines(lines, 2))
    }

    @Test
    fun countPointsWithOverlappingLines() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day5.txt")
        val hydrothermalVents = HydrothermalVents()

        assertEquals(6710, hydrothermalVents.countPointsWithOverlappingLines(lines, 2))
    }

    @Test
    fun countPointsWithOverlappingAllLines() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day5.txt")
        val hydrothermalVents = HydrothermalVents()

        assertEquals(20121, hydrothermalVents.countPointsWithOverlappingAllLines(lines, 2))
    }
}