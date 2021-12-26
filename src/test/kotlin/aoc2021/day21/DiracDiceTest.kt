package aoc2021.day21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DiracDiceTest {
    @Test
    fun part1() {
        val diracDice = DiracDice()

        assertEquals(412344, diracDice.part1(8, 3))
    }

    @Test
    fun cubes() {
        var value = 3L

        repeat(20) {
            println(value)

            value *= 3
        }
    }
}