package aoc2021.day15

import java.util.*

class Chiton {
    fun lowestTotalRisk(lines: List<String>):Int {
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
    ):Int? {
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

        if (currentScore != 0) {
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

        var min:Int? = null

        points.forEach { p ->
            val s = traverse(lines, p, localVisitedPoints, goalPoint, score)

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
}

data class Point(val x: Int, val y: Int)