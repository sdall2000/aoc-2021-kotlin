package aoc2021.day1

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SonarSweepTest {
    @Test
    fun countDepthIncreasesGroup1() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1121, sonarSweep.countDepthIncreases(input, 1))
    }

    @Test
    fun countDepthIncreasesGroup3() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1065, sonarSweep.countDepthIncreases(input, 3))
    }
}