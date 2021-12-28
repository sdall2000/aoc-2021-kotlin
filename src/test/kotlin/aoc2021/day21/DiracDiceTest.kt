package aoc2021.day21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DiracDiceTest {
    @Test
    fun part1() {
        val diracDice = DiracDice()

        assertEquals(412_344, diracDice.part1(8, 3))
    }

    @Test
    fun part2() {
        val diracDice = DiracDice()

        assertEquals(214_924_284_932_572, diracDice.part2(8, 3))
    }
}