package aoc2021.day17

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TrickShotTest {

    @Test
    fun part1() {
        val trickShot = TrickShot()

        assertEquals(10878, trickShot.part1(PUZZLE_INPUT))
    }

    @Test
    fun part1Sample() {
        val trickShot = TrickShot()

        assertEquals(45, trickShot.part1(SAMPLE_PUZZLE_INPUT))
    }

    @Test
    fun part2() {
        val trickShot = TrickShot()

        assertEquals(4716, trickShot.part2(PUZZLE_INPUT))
    }

    @Test
    fun part2Sample() {
        val trickShot = TrickShot()

        assertEquals(112, trickShot.part2(SAMPLE_PUZZLE_INPUT))
    }

    companion object {
        private const val PUZZLE_INPUT = "target area: x=139..187, y=-148..-89"
        private const val SAMPLE_PUZZLE_INPUT = "target area: x=20..30, y=-10..-5"
    }
}