package aoc2021.day6

class Lanternfish {
    fun getLanternFishPopulation(lanternFish: List<Int>, days: Int): Long {
        // Declare an array where the index represents the timer.  For this problem that will range from 0 to 8.
        val lanternFishTimer = LongArray(9)

        // Seed the lanterFishTimer array with the lanternFish parameter
        lanternFish.forEach { lanternFishTimer[it]++ }

        repeat(days) {
            // Save off the count of timers that are zero.  This is how many new fish we need to spawn.
            // It also represents how many parent fish are added to the 6th index.
            val newSpawns = lanternFishTimer[0]

            lanternFishTimer.copyInto(lanternFishTimer, 0, 1)

            lanternFishTimer[8] = newSpawns
            lanternFishTimer[6] += newSpawns
        }

        return lanternFishTimer.sum()
    }
}