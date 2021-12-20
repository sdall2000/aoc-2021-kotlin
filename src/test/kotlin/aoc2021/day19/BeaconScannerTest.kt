package aoc2021.day19

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BeaconScannerTest {

    @Test
    fun getBeaconCount() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day19.txt")

        val beaconScanner = BeaconScanner()

        assertEquals(-1, beaconScanner.getBeaconCount(lines))
    }

    @Test
    fun getBeaconCountSample() {
        val lines = PuzzleInputTestUtils.getPuzzleInputLines("day19Sample.txt")

        val beaconScanner = BeaconScanner()

        assertEquals(79, beaconScanner.getBeaconCount(lines))
    }
}