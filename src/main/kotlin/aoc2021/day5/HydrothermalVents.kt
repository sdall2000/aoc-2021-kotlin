package aoc2021.day5

import kotlin.math.abs

class HydrothermalVents {
    fun countPointsWithOverlappingLines(input: List<String>, pointThreshold: Int): Int {
        val lines = buildLines(input, false)
        return countPointOverlaps(lines, pointThreshold)
    }

    fun countPointsWithOverlappingAllLines(input: List<String>, pointThreshold: Int): Int {
        val lines = buildLines(input, true)
        return countPointOverlaps(lines, pointThreshold)
    }

    private fun buildLines(input: List<String>, includeDiagonal: Boolean): List<Line> {
        val lines = mutableListOf<Line>()

        input.forEach {
            // 0,9 -> 5,9
            val split1 = it.split(" -> ")
            val (x1, y1) = split1[0].split(",")
            val (x2, y2) = split1[1].split(",")

            val point1 = Point(x1.toInt(), y1.toInt())
            val point2 = Point(x2.toInt(), y2.toInt())

            if (includeDiagonal || (point1.x == point2.x || point1.y == point2.y)) {
                val line = Line(point1, point2)
                lines.add(line)
            }
        }

        return lines
    }

    private fun countPointOverlaps(lines: List<Line>, pointThreshold: Int): Int {
        // Container for storing total counts per point
        val pointMap = mutableMapOf<Point, Int>()

        // Groups by point and their counts
        lines.forEach { line ->
            line.getPoints().groupingBy { it }.eachCountTo(pointMap)
        }

        return pointMap.count { it.value >= pointThreshold }
    }
}

data class Point(val x: Int, val y: Int)

data class Line(val point1: Point, val point2: Point) {
    fun getPoints(): List<Point> {
        // Using compareTo will give us the correct increment for the given axis.
        // Either -1, 0, or 1.
        val xInc = point2.x.compareTo(point1.x)
        val yInc = point2.y.compareTo(point1.y)

        // Calculate the total number of points for the line segment.
        val totalPoints =
            abs(point1.x - point2.x).coerceAtLeast(abs(point1.y - point2.y)) + 1

        val points = mutableListOf<Point>()

        // Starting x/y
        var x = point1.x
        var y = point1.y

        repeat(totalPoints) {
            points.add(Point(x, y))
            x += xInc
            y += yInc
        }

        return points
    }
}