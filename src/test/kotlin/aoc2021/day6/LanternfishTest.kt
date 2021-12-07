package aoc2021.day6

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LanternfishTest {

    @Test
    fun countLanternfishGrowthSample18() {
        val lanternFishCounts = listOf(3, 4, 3, 1, 2)

        val lanternfish = Lanternfish()

        assertEquals(26, lanternfish.getLanternFishPopulation(lanternFishCounts, 18))
    }

    @Test
    fun countLanternfishGrowthSample80() {
        val lanternFishCounts = listOf(3, 4, 3, 1, 2)

        val lanternfish = Lanternfish()

        assertEquals(5934, lanternfish.getLanternFishPopulation(lanternFishCounts, 80))
    }

    @Test
    fun countLanternfishGrowth80() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day6.txt")
        val lanternFishCounts = lines[0].split(",").map {
            it.toInt()
        }

        val lanternfish = Lanternfish()

        assertEquals(343441, lanternfish.getLanternFishPopulation(lanternFishCounts, 80))
    }

    @Test
    fun countLanternfishGrowth256() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day6.txt")
        val lanternFishCounts = lines[0].split(",").map {
            it.toInt()
        }

        val lanternfish = Lanternfish()

        assertEquals(1569108373832, lanternfish.getLanternFishPopulation(lanternFishCounts, 256))
    }
}