package aoc2021.day1

import java.util.*

class SonarSweep {
    // Count the depth increases.  Depths is the list of depts to evaluate.  Group size is how many depths
    // make up a single group.
    fun countDepthIncreases(depths: List<Int>, groupSize: Int): Int {
        var count = 0

        val previousQueue: Queue<Int> = LinkedList()
        var previousSum = 0

        depths.forEach { depth ->
            // Make sure we have filled up the previous depth collection to the appropriate group size
            if (previousQueue.size == groupSize) {
                // Remove the first item from the queue.
                val removed = previousQueue.remove()

                // Add the new item to the queue.
                previousQueue.add(depth)

                // We can calculate the current sum, by subtracting the removed item from the previous sum
                // and adding the current depth.
                val currentSum = previousSum - removed + depth

                // See if the current sum is greater than the previous sum.
                if (currentSum > previousSum) {
                    count++
                }

                // Assign the previous sum to the current sum for the next iteration
                previousSum = currentSum
            } else {
                // Continue doing the initial build up of the queue.
                previousQueue.add(depth)

                // Add as we go instead of waiting for the queue to be filled.
                previousSum += depth
            }
        }

        return count
    }

    // JetBrains solution (and James Campbell also found this).
    fun countDepthIncreasesJetBrainsWindowed(depths: List<Int>, groupSize: Int): Int {
        // Uses Kotlin's windowed API.
        // windowed returns a List of a List based on group size.
        // So, 1, 2, 3, 4, 5, 6 with a group size of 3 would be
        // (1, 2, 3), (2, 3, 4), (3, 4, 5), (4, 5, 6)
        return depths
            .windowed(groupSize)
            // This call to windowed then breaks that list into two sets, that ultimately will be
            // ((1, 2, 3), (2, 3, 4)), ((2, 3, 4), (3, 4, 5)), ((3, 4, 5), (4, 5, 6))
            .windowed(2).count { (a, b) ->
                // And here we sum the two lists being compared.
                a.sum() < b.sum()
            }
    }

    // Inspired from an insight in the JetBrains video for this challenge.
    // If we are comparing numbers A + B + C with B + C + D, we are really only comparing
    // A to D.
    fun countDepthIncreasesJetBrainsOptimization(depths: List<Int>, groupSize: Int): Int {
        return depths
            // With the same sample input as the previous input, we would get these windows
            // (1, 2, 3, 4), (2, 3, 4, 5), (3, 4, 5, 6)
            .windowed(groupSize + 1)
            .count {
                // And we just compare the first and last elements
                it[0] < it[groupSize]
            }
    }

    // Could make a case of not using .windowed at all and just indexing directly into the depths list.
    // Certainly that would be more performant
    // TODO this works but can be improved
    fun countDepthIncreasesBest(depths: List<Int>, groupSize: Int): Int {
        var count = 0

        var secondIndex = groupSize
        var firstIndex = 0

        while (secondIndex < depths.size) {
            if (depths[firstIndex] < depths[secondIndex]) {
                count++
            }

            firstIndex++
            secondIndex++
        }

        return count
    }
}