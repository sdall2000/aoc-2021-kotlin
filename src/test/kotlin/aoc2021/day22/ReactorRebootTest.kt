package aoc2021.day22

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ReactorRebootTest {
    @Test
    fun countOnCubesTest() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day22.txt")

        val reactorReboot = ReactorReboot()

        assertEquals(623748, reactorReboot.countOnCubes2(lines, 50))
    }

    @Test
    fun countOnCubesNoFilterTest() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day22.txt")

        val reactorReboot = ReactorReboot()

        // 600563608709 Too low
        assertEquals(623748, reactorReboot.countOnCubes2(lines))
    }
}