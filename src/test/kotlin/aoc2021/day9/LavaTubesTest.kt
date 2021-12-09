package aoc2021.day9

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LavaTubesTest {

    @Test
    fun sumLowPointRiskLevels() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day9.txt")

        val lavaTubes = LavaTubes()

        assertEquals(560, lavaTubes.sumLowPointRiskLevels(lines))
    }

    @Test
    fun multiplyThreeLargestBasins() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day9.txt")

        val lavaTubes = LavaTubes()

        assertEquals(959136, lavaTubes.multiplyThreeLargestBasins(lines))
    }
}