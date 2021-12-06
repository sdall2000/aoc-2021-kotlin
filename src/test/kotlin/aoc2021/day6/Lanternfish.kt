package aoc2021.day6

class Lanternfish {
    fun countLanternfishGrowth(lanternFish:List<Int>, days:Int): Long {

        // Convert to a map<Timer, Count>
        // Where Timer is a possible timer number, 0-8.
        // Count is how many lanternfish of those timers exist.
        val mutableLanternFish = mutableMapOf<Int, Long>()

        lanternFish.forEach { addLanternfishTimer(mutableLanternFish, it)}

//        println("Start: $mutableLanternFish")

        repeat(days) {
            val newSpawns = mutableLanternFish[0]

            for (i in 0..7) {
                val nextCount = mutableLanternFish[i+1]

                if (nextCount != null) {
                    mutableLanternFish[i] = nextCount
                } else {
                    mutableLanternFish[i] = 0
                }
            }

            if (newSpawns != null) {
                mutableLanternFish[8] = newSpawns
                // Add the fish that spawned another fish to the "6" timer
                mutableLanternFish[6] = mutableLanternFish[6]!! + newSpawns
            } else {
                mutableLanternFish[8] = 0
            }

//            println(mutableLanternFish)
        }

        var sum:Long = 0

        mutableLanternFish.forEach { (_, b) -> sum+=b}

        return sum
    }

    private fun addLanternfishTimer(timerMap:MutableMap<Int, Long>, timer:Int) {
        if (timerMap.contains(timer)) {
            timerMap[timer] = timerMap[timer]!! + 1
        } else {
            timerMap[timer] = 1
        }
    }
}