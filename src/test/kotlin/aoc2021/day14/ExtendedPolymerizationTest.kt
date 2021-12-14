package aoc2021.day14

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ExtendedPolymerizationTest {

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(2435, extendedPolymerization.part1(lines, 10))
    }

    @Test
    fun part2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(0, extendedPolymerization.part1(lines, 40))
    }

    @Test
    fun part1New() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(2435, extendedPolymerization.part2New(lines, 10))
    }

    @Test
    fun part2New() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(0, extendedPolymerization.part2New(lines, 40))
    }

    @Test
    fun part1Sample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day14Sample.txt")

        val extendedPolymerization = ExtendedPolymerization()

        assertEquals(1588, extendedPolymerization.part1(lines, 10))
    }
}