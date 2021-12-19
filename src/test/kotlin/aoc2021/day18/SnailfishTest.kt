package aoc2021.day18

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SnailfishTest {

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day18.txt")

        val snailfish = Snailfish()

        assertEquals(4243, snailfish.part1(lines))
    }

    @Test
    fun part2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day18.txt")

        val snailfish = Snailfish()

        assertEquals(4701, snailfish.part2(lines))
    }

    @Test
    fun snailPairExplode() {
        val snailPair = SnailPair.parse("[[[[[9,8],1],2],3],4]")
        snailPair!!.reduce()

        val snailPair2 = SnailPair.parse("[7,[6,[5,[4,[3,2]]]]]")
        snailPair2!!.reduce()

        val snailPair3 = SnailPair.parse("[[6,[5,[4,[3,2]]]],1]")
        snailPair3!!.reduce()

        val snailPair4 = SnailPair.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        snailPair4!!.reduce()

        val snailPair5 = SnailPair.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
        snailPair5!!.reduce()
    }
}