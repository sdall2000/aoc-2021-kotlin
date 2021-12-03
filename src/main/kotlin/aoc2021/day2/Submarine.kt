package aoc2021.day2

class Submarine {
    fun calculatePosition(course: List<String>): Int {
        var depth = 0
        var horizontalPosition = 0

        course.forEach { line ->
            val split = line.split(" ")
            val command = split[0]
            val distance = split[1].toInt()

            when (command) {
                "forward" -> horizontalPosition += distance
                "up" -> depth -= distance
                "down" -> depth += distance
            }
        }

        return depth * horizontalPosition
    }

    fun calculatePositionPart2(course: List<String>): Int {
        var depth = 0
        var horizontalPosition = 0
        var aim = 0

        course.forEach { line ->
            val split = line.split(" ")
            val command = split[0]
            val distance = split[1].toInt()

            when (command) {
                "forward" -> {
                    horizontalPosition += distance
                    depth += aim * distance
                }
                "up" -> aim -= distance
                "down" -> aim += distance
            }
        }

        return depth * horizontalPosition
    }
}