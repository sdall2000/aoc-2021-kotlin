package aoc2021.day15

import java.util.*

class Chiton {
    fun lowestTotalRisk(lines: List<String>): Int {
        var x = 0
        var y = 0

        val goalY = lines.size - 1
        val goalX = lines[0].length - 1

        val goalPoint = Point(goalX, goalY)

        val visitedPoints = ArrayList<Point>()

        val point = Point(x, y)

//        visitedPoints.add(point)

        return traverse(lines, point, visitedPoints, goalPoint, 0, false)!!
    }

    private fun traverse(
        lines: List<String>,
        tryPoint: Point,
        visitedPoints: List<Point>,
        goalPoint: Point,
        currentScore: Int,
        debug: Boolean = false
    ): Int? {
        if (debug) {
            println("Path so far: ")
            visitedPoints.forEach { p ->
                println("   $p")
            }
        }

        if (debug) println("Trying position $tryPoint.  Current score is $currentScore")

        if (!isValidPosition(lines, tryPoint, visitedPoints)) {
            if (debug) println("Position is not valid.")
            return null
        }

        // Don't count the starting square in the score.
        var score = 0

        if (tryPoint != Point(0, 0)) {
            score = currentScore + lines[tryPoint.y][tryPoint.x].digitToInt()
        }

        if (debug) println("Current point score is ${lines[tryPoint.y][tryPoint.x]} for a total of $score")

        if (tryPoint == goalPoint) {
            if (debug) println("Reached the final point, total score is $score")
            return score
        }

        val localVisitedPoints = ArrayList<Point>()
        localVisitedPoints.addAll(visitedPoints)
        localVisitedPoints.add(tryPoint)

//        val north = Point(tryPoint.x, tryPoint.y - 1)
        val east = Point(tryPoint.x + 1, tryPoint.y)
        val south = Point(tryPoint.x, tryPoint.y + 1)
//        val west = Point(tryPoint.x - 1, tryPoint.y)

//        val points = arrayOf(north, east, south, west)
        val points = arrayOf(east, south)

        var min: Int? = null

        points.forEach { p ->
            val s = traverse(lines, p, localVisitedPoints, goalPoint, score, debug)

            if (s != null) {
                if (min == null || s < min!!) {
                    min = s
                }
            }
        }

//        visitedPoints.remove(point)

        if (debug) println("Returning min of $min")
        return min
    }

    private fun isValidPosition(lines: List<String>, point: Point, visitedPoints: List<Point>): Boolean {
        return point.y in lines.indices && point.x in lines[0].indices && !visitedPoints.contains(point)
    }

    fun d(lines: List<String>): Int {
        var x = 0
        var y = 0

        val goalY = lines.size - 1
        val goalX = lines[0].length - 1

        // Maps a position to a node.
        val nodeMap = HashMap<Point, Node>()

        for (y in lines.indices) {
            val row = lines[y]
            for (x in lines[0].indices) {
                val point = Point(x, y)
                val node = Node(point, row[x].digitToInt())

                nodeMap[point] = node
            }
        }

        // Now that we have a mapping of points to nodes, let's build the relationships between each node.
        val nodeLinkMap = HashMap<Node, Set<Node>>()

        nodeMap.forEach { (point, node) ->
            val neighborNodeSet = HashSet<Node>()

            // The four possible neighbor points to try.
            val north = Point(point.x, point.y - 1)
            val east = Point(point.x + 1, point.y)
            val south = Point(point.x, point.y + 1)
            val west = Point(point.x - 1, point.y)

            arrayOf(north, east, south, west).filter {
                isPointValid(lines, it)
            }.forEach {
                neighborNodeSet.add(nodeMap[it]!!)
            }

            nodeLinkMap[node] = neighborNodeSet
        }

        val goalPoint = Point(goalX, goalY)

        return 0

    }

    private fun isPointValid(lines: List<String>, point: Point):Boolean {
        return point.y in lines.indices && point.x in lines[0].indices
    }
}


data class Node(val point: Point, val cost: Int) : Comparator<Node> {
    override fun compare(o1: Node?, o2: Node?): Int {
        if (o1!!.cost < o2!!.cost) {
            return -1
        } else if (o1.cost > o2.cost) {
            return 1
        }

        return 0
    }
}

data class Point(val x: Int, val y: Int)