package aoc2021.day2

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SubmarineTest {

    @Test
    fun calculatePosition() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day2.txt")
        val submarine = Submarine()

        assertEquals(1451208, submarine.calculatePosition(lines))
    }

    @Test
    fun calculatePositionPart2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day2.txt")
        val submarine = Submarine()

        assertEquals(1620141160, submarine.calculatePositionPart2(lines))
    }
}