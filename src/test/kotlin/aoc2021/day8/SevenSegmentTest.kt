package aoc2021.day8

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SevenSegmentTest {

    @Test
    fun part1Sample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day8Sample.txt")

        val sevenSegment = SevenSegment()

        assertEquals(26, sevenSegment.part1(lines))
    }

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day8.txt")

        val sevenSegment = SevenSegment()

        assertEquals(521, sevenSegment.part1(lines))
    }

    @Test
    fun part2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day8.txt")

        val sevenSegment = SevenSegment()

        assertEquals(1016804, sevenSegment.part2(lines))
    }

    @Test
    fun part2Sample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day8Sample.txt")

        val sevenSegment = SevenSegment()

        assertEquals(61229, sevenSegment.part2(lines))
    }

    @Test
    fun part2SingleLine() {
        val lines = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

        val sevenSegment = SevenSegment()

        assertEquals(5353, sevenSegment.part2(lines))
    }
}