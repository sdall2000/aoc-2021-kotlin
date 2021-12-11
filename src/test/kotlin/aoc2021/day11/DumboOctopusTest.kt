package aoc2021.day11

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DumboOctopusTest {

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day11.txt")

        val octopusGrid = lines.map {
            it.map {
                it.toString().toInt()
            }.toIntArray()
        }.toTypedArray()

        val dumboOctopus = DumboOctopus()

        assertEquals(1723, dumboOctopus.part1(octopusGrid))
    }

    @Test
    fun part2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day11.txt")

        val octopusGrid = lines.map {
            it.map {
                it.toString().toInt()
            }.toIntArray()
        }.toTypedArray()

        val dumboOctopus = DumboOctopus()

        assertEquals(327, dumboOctopus.part2(octopusGrid))
    }

}