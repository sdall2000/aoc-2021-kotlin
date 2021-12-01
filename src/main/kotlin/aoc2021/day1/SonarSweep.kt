package aoc2021.day1

import java.util.*
import kotlin.collections.ArrayList

class SonarSweep {
    // Count the depth increases.  Depths is the list of depts to evaluate.  Group size is how many depths
    // make up a single group.
    fun countDepthIncreases(depths: List<Int>, groupSize: Int): Int {
        var count = 0

        val previousQueue: Queue<Int> = LinkedList()
        var previousSum = 0

        depths.forEach {depth ->
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
}