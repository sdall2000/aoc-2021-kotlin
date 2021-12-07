package aoc2021.day7

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CrabAlignmentTest {

    @Test
    fun getMinimumLinearFuelBurn() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day7.txt")

        val crabPositions = lines[0].split(",").map {
            it.toInt()
        }.toIntArray()

        val crabAlignment = CrabAlignment()

        assertEquals(336120, crabAlignment.getMinimumLinearFuelBurn(crabPositions))
    }

    @Test
    fun getMinimumFuelBurn() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day7.txt")

        val crabPositions = lines[0].split(",").map {
            it.toInt()
        }.toIntArray()

        val crabAlignment = CrabAlignment()

        assertEquals(96864235, crabAlignment.getMinimumIncrementalFuelBurn(crabPositions))
    }

    @Test
    fun getMinimumLinearFuelBurnSample() {
        val crabPositions = CrabAlignment()

        assertEquals(37, crabPositions.getMinimumLinearFuelBurn(intArrayOf(16,1,2,0,4,2,7,1,2,14)))
    }

    @Test
    fun getMinimumFuelBurnSample() {
        val crabPositions = CrabAlignment()

        assertEquals(168, crabPositions.getMinimumIncrementalFuelBurn(intArrayOf(16,1,2,0,4,2,7,1,2,14)))
    }
}