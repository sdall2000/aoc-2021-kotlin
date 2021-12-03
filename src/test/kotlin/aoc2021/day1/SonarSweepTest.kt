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

    @Test
    fun countDepthIncreasesGroup1JetBrains() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1121, sonarSweep.countDepthIncreasesJetBrainsWindowed(input, 1))
    }

    @Test
    fun countDepthIncreasesGroup3JetBrains() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1065, sonarSweep.countDepthIncreasesJetBrainsWindowed(input, 3))
    }

    @Test
    fun countDepthIncreasesGroup1JetBrainsOptimization() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1121, sonarSweep.countDepthIncreasesJetBrainsOptimization(input, 1))
    }

    @Test
    fun countDepthIncreasesGroup3JetBrainsOptimization() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1065, sonarSweep.countDepthIncreasesJetBrainsOptimization(input, 3))
    }

    @Test
    fun countDepthIncreasesGroup1Best() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1121, sonarSweep.countDepthIncreasesBest(input, 1))
    }

    @Test
    fun countDepthIncreasesGroup3Best() {
        val input = PuzzleInputTestUtils.getPuzzleInputLinesIntList("day1.txt")

        val sonarSweep = SonarSweep()
        assertEquals(1065, sonarSweep.countDepthIncreasesBest(input, 3))
    }

}