package aoc2021.day12

class PassagePathing {
    fun countPathsSmallCavesOnce(lines: List<String>): Int {

        val caveMap = HashMap<String, MutableSet<String>>()

        // Build map of a given cave to all caves it is connected to
        lines.forEach {
            val (caveName1, caveName2) = it.split("-")
            addPairToCaveMap(caveMap, caveName1, caveName2)
        }

        return traverse(caveMap, "start", HashSet(), "start")
    }

    private fun traverse(
        caveMap: Map<String, Set<String>>,
        currentCave: String,
        smallCavesVisited: MutableSet<String>,
        solution: String
    ): Int {
        if (currentCave == "end") {
//            println(solution)
            return 1
        }

        // We already visited this cave
        if (smallCavesVisited.contains(currentCave)) {
//            println("    Small cave $currentCave already visited")
            return 0
        }

        val localSmallCavesVisited = HashSet<String>()

        if (isCaveSmall(currentCave)) {
            smallCavesVisited.add(currentCave)
            localSmallCavesVisited.add(currentCave)
        }

        var total = 0

//        println("Traversing cave $currentCave")

        caveMap[currentCave]?.forEach { cave ->
//            println("  child $cave")
            total += traverse(caveMap, cave, smallCavesVisited, "$solution$cave ")
        }

        // Unwind the caves visited so they can be used in the next try.
        smallCavesVisited.removeAll(localSmallCavesVisited)

        return total
    }


    fun countPathsAllowOneSmallCaveTwice(lines: List<String>): Int {

        val caveMap = HashMap<String, MutableSet<String>>()

        // Build map of a given cave to all caves it is connected to
        lines.forEach {
            val (caveName1, caveName2) = it.split("-")
            addPairToCaveMap(caveMap, caveName1, caveName2)
        }

        return traverseAllowOneSmallCaveTwice(caveMap, "start", HashSet(), "", "start")
    }

    private fun traverseAllowOneSmallCaveTwice(
        caveMap: Map<String, Set<String>>,
        currentCave: String,
        smallCavesVisited: MutableSet<String>,
        secondSmallCaveTried: String,
        solution: String
    ): Int {
        // We successfully reached the end cave.
        if (currentCave == "end") {
            return 1
        }

        var localSecondSmallCaveTried = secondSmallCaveTried

        // We already visited this cave
        if (smallCavesVisited.contains(currentCave)) {
            // See if we can still visit a small cave for the second time (except for "start" which can only be visited once).
            if (secondSmallCaveTried.isEmpty() && currentCave != "start") {
                localSecondSmallCaveTried = currentCave
            } else {
                // Path is not valid
                return 0
            }
        }

        val localSmallCavesVisited = HashSet<String>()

        if (isCaveSmall(currentCave) && currentCave != localSecondSmallCaveTried) {
            smallCavesVisited.add(currentCave)
            localSmallCavesVisited.add(currentCave)
        }

        var total = 0

//        println("Traversing cave $currentCave")

        caveMap[currentCave]?.forEach { cave ->
//            println("  child $cave")
            total += traverseAllowOneSmallCaveTwice(
                caveMap,
                cave,
                smallCavesVisited,
                localSecondSmallCaveTried,
                "$solution,$cave"
            )
        }

        // Unwind the caves visited so they can be used in the next try.
        smallCavesVisited.removeAll(localSmallCavesVisited)

        return total
    }

    private fun addPairToCaveMap(
        caveMap: MutableMap<String, MutableSet<String>>,
        caveName1: String,
        caveName2: String
    ) {
        addCaveToCaveMap(caveMap, caveName1, caveName2)
        addCaveToCaveMap(caveMap, caveName2, caveName1)
    }

    private fun addCaveToCaveMap(
        caveMap: MutableMap<String, MutableSet<String>>,
        caveName1: String,
        caveName2: String
    ) {
        var set = caveMap[caveName1]
        if (set == null) {
            set = mutableSetOf(caveName2)
            caveMap[caveName1] = set
        } else {
            set.add(caveName2)
        }
    }

    private fun isCaveSmall(cave: String): Boolean {
        return cave == cave.lowercase()
    }
}