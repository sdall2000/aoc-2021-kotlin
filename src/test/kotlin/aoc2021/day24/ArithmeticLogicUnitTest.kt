package aoc2021.day24

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ArithmeticLogicUnitTest {

    @Test
    fun largestModelNumber() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day24.txt")

        val arithmeticLogicUnit = ArithmeticLogicUnit()

        assertEquals(-1, arithmeticLogicUnit.largestModelNumber(lines))
    }

    @Test
    fun largestModelNumber2() {
        val arithmeticLogicUnit = ArithmeticLogicUnit2()

        assertEquals(99_893_999_291_967, arithmeticLogicUnit.largestModelNumber())
    }

    @Test
    fun smallestModelNumber2() {
        val arithmeticLogicUnit = ArithmeticLogicUnit2()

        // 39893999291961 too high

        assertEquals(-1, arithmeticLogicUnit.smallestModelNumber())
    }
}