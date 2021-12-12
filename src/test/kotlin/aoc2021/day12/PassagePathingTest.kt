package aoc2021.day12

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PassagePathingTest {

    @Test
    fun countPathsSmallCavesOnceSample1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day12Sample1.txt")

        val passagePathing = PassagePathing()

        assertEquals(10, passagePathing.countPathsSmallCavesOnce(lines))
    }

    @Test
    fun countPathsSmallCavesOnce() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day12.txt")

        val passagePathing = PassagePathing()

        assertEquals(5254, passagePathing.countPathsSmallCavesOnce(lines))
    }

    @Test
    fun countPathsAllowOneSmallCaveTwice3() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day12.txt")

        val passagePathing = PassagePathing()

        assertEquals(149385, passagePathing.countPathsAllowOneSmallCaveTwice(lines))
    }

    @Test
    fun countPathsAllowOneSmallCaveTwiceSample13() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day12Sample1.txt")

        val passagePathing = PassagePathing()

        assertEquals(36, passagePathing.countPathsAllowOneSmallCaveTwice(lines))
    }
}