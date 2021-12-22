package aoc2021.day21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DiracDiceTest {
    @Test
    fun part1() {
        val diracDice = DiracDice()
        // 113139 too low
        assertEquals(0, diracDice.part1(8, 3))
    }
}