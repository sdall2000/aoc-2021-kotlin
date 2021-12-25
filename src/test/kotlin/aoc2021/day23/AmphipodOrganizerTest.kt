package aoc2021.day23

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AmphipodOrganizerTest {

    @Test
    fun part1() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day23.txt")

        val amphipodOrganizer = AmphipodOrganizer()

        assertEquals(19059, amphipodOrganizer.part1(lines))
    }

    @Test
    fun part2() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day23.txt")

        val amphipodOrganizer = AmphipodOrganizer()

        val newLines = mutableListOf<String>()

        lines.forEachIndexed { lineIndex, line ->
            if (lineIndex == 3) {
                newLines.add("  #D#C#B#A#")
                newLines.add("  #D#B#A#C#")
            }

            newLines.add(line)
        }

        assertEquals(48541, amphipodOrganizer.part1(newLines))
    }
}