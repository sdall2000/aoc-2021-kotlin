package aoc2021.day14

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ExtendedPolymerizationTest {

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(2_435, extendedPolymerization.maxMinusMinCount(lines, 10))
    }

    @Test
    fun part2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(2_587_447_599_164, extendedPolymerization.maxMinusMinCount(lines, 40))
    }
}