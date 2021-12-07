package aoc2021.day7

import kotlin.math.abs

class CrabAlignment {
    fun getMinimumLinearFuelBurn(crabPositions: IntArray): Int {
        return getMinimumFuelBurn(crabPositions, linearFuelBurn)
    }

    fun getMinimumIncrementalFuelBurn(crabPositions: IntArray): Int {
        return getMinimumFuelBurn(crabPositions, incrementalFuelBurn)
    }

    private fun getMinimumFuelBurn(crabPositions: IntArray, fuelBurn: (Int) -> Int): Int {
        // We need to consider all positions, not just the crab positions that happened to be in the list.
        // Iterate through each number between the min/max values in the list.
        val minHorizontalPosition = crabPositions.minOrNull()!!
        val maxHorizontalPosition = crabPositions.maxOrNull()!!

        val positions = List(maxHorizontalPosition - minHorizontalPosition + 1) { it + minHorizontalPosition }

        return positions.minOf {
            getFuelBurn(crabPositions, it, fuelBurn)
        }
    }

    // Calculate the fuel burn required for each crab to move to the target position,
    // with the given fuel formula.
    private fun getFuelBurn(
        crabPositions: IntArray,
        targetPosition: Int,
        fuelBurn: (Int) -> Int
    ): Int {
        return crabPositions.sumOf { fuelBurn(abs(targetPosition - it)) }
    }

    private val linearFuelBurn: (Int) -> Int = { moves: Int -> moves }

    private val incrementalFuelBurn: (Int) -> Int = { moves: Int ->
        var fuelBurn = 0

        for (i in 1..moves) {
            fuelBurn += i
        }

        fuelBurn
    }
}