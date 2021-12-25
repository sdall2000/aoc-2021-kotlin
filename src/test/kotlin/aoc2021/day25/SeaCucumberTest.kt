package aoc2021.day25

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SeaCucumberTest {

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day25.txt")

        val seaCucumber = SeaCucumber()

        assertEquals(400, seaCucumber.part1(lines))
    }

    @Test
    fun part1Sample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day25Sample.txt")

        val seaCucumber = SeaCucumber()

        assertEquals(58, seaCucumber.part1(lines))
    }
}