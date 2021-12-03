package aoc2021.day3

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BinaryDiagnosticTest {

    @Test
    fun calculatePowerConsumption() {
        val puzzleInput = PuzzleInputTestUtils.getPuzzleInputLines("day3.txt")
        val binaryDiagnostic = BinaryDiagnostic()

        assertEquals(1082324, binaryDiagnostic.calculatePowerConsumption(puzzleInput))
    }

    @Test
    fun calculateLifeSupportRating() {
        val puzzleInput = PuzzleInputTestUtils.getPuzzleInputLines("day3.txt")
        val binaryDiagnostic = BinaryDiagnostic()

        assertEquals(1353024, binaryDiagnostic.calculateLifeSupportRating(puzzleInput))
    }

    @Test
    fun calculatePowerConsumptionSampleInput() {
        val puzzleInput = PuzzleInputTestUtils.getPuzzleInputLines("day3Sample.txt")
        val binaryDiagnostic = BinaryDiagnostic()

        assertEquals(198, binaryDiagnostic.calculatePowerConsumption(puzzleInput))
    }

    @Test
    fun calculateLifeSupportRatingSampleInput() {
        val puzzleInput = PuzzleInputTestUtils.getPuzzleInputLines("day3Sample.txt")
        val binaryDiagnostic = BinaryDiagnostic()

        assertEquals(230, binaryDiagnostic.calculateLifeSupportRating(puzzleInput))
    }
}