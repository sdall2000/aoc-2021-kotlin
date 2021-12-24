package aoc2021.day22

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ReactorRebootTest {
    @Test
    fun countOnCubesPart1Test() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day22.txt")

        val reactorReboot = ReactorReboot()

        assertEquals(623748, reactorReboot.countOnCubes(lines, 50))
    }

    @Test
    fun countOnCubesPart2Test() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day22.txt")

        val reactorReboot = ReactorReboot()

        assertEquals(1227345351869476, reactorReboot.countOnCubes(lines))
    }

    @Test
    fun subtractionTest1() {
        val cube = Cube(-5, 5, -5, 5, -5, 5)
        val cube2 = Cube(0, 1, 0, 1, 0, 1)

        val cubesAfterSubtract = cube.remove(cube2)

        assertEquals(6, cubesAfterSubtract.size)

        val topCube = Cube(-5, 5, 2, 5, -5, 5)
        val bottomCube = Cube(-5, 5, -5, -1, -5, 5)
        val rightCube = Cube(2, 5, 0, 1, -5, 5)
        val leftCube = Cube(-5, -1, 0, 1, -5, 5)
        val backCube = Cube(0, 1, 0, 1, -5, -1)
        val frontCube = Cube(0, 1, 0, 1, 2, 5)

        assertTrue(cubesAfterSubtract.contains(topCube))
        assertTrue(cubesAfterSubtract.contains(bottomCube))
        assertTrue(cubesAfterSubtract.contains(rightCube))
        assertTrue(cubesAfterSubtract.contains(leftCube))
        assertTrue(cubesAfterSubtract.contains(backCube))
        assertTrue(cubesAfterSubtract.contains(frontCube))
    }

    @Test
    fun subtractionTest2() {
        val cube = Cube(13, 26, -18, -17, -12, -3)
        val cube2 = Cube(-8, 44, -30, 20, -19, 29)

        val cubesAfterSubtract = cube.remove(cube2)

        // Cube2 completely contains cube, so there should be nothing left after subtracting.
        assertEquals(0, cubesAfterSubtract.size)
    }
}