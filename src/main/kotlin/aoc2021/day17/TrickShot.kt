package aoc2021.day17

import kotlin.math.abs

class TrickShot {

    fun part1(input: String): Int {
        var parse = input.substringAfter("target area: ")
        val delimitComma = parse.split(",")
        val xString = delimitComma[0].trim().substringAfter("x=")
        val yString = delimitComma[1].trim().substringAfter("y=")

        val (x1str, x2str) = xString.split("..")
        val (y1str, y2str) = yString.split("..")

        val x1 = x1str.toInt()
        val x2 = x2str.toInt()

        val y1 = y1str.toInt()
        val y2 = y2str.toInt()

        val targetArea = TargetArea(x1, x2, y1, y2)

        // Determine the range of x velocities that can possibly reach the target area.

        // Assuming the y velocity at x=0 will be the same as what it was on the way up (except the sign is different)
        // I.e., a y velocity starting at 10 will be -10 by the time it gets back to the x=0 level.
        // To hit the target, the y velocity when it reaches x=0 must not exceed minY.

        var yVelocityRange = 1..abs(targetArea.smallestY())

        var xVelocityRange = validXVelocityRange(targetArea, xTransformVelocity)

        println("Y velocities: $yVelocityRange")
        println("X velocities: $xVelocityRange")

        var maxYVelocity = 0
        var highestYPosition = 0

        yVelocityRange.forEach {yVelocity ->
            xVelocityRange.forEach { xVelocity ->
                var x = 0
                var y = 0
                var highestY = 0

                var xCurrentVelocity = xVelocity
                var yCurrentVelocity = yVelocity

                // Given these two velocities, repeat until Y falls below the target area.
                while (!targetArea.isYBelow(y)) {
                    x += xCurrentVelocity
                    y += yCurrentVelocity

                    highestY = highestY.coerceAtLeast(y)

                    if (targetArea.contains(x, y)) {
                        if (yVelocity > maxYVelocity) {
                            maxYVelocity = yVelocity
                            highestYPosition = highestY
                        }
                    }

                    xCurrentVelocity = xTransformVelocity(xCurrentVelocity)
                    yCurrentVelocity = yTransformVelocity(yCurrentVelocity)
                }
            }
        }

        return highestYPosition
    }

    private fun validXVelocityRange(targetArea: TargetArea, xTransform: (Int) -> Int): Set<Int> {
        val xTryVelocity = 1..targetArea.x2

        val velocities = HashSet<Int>()

        xTryVelocity.forEach { velocity ->
            var xPosition = 0

            var v = velocity

            do {
                xPosition += v
                if (targetArea.containsX(xPosition)) {
                    velocities.add(velocity)
                }
                v = xTransform(v)
            } while (v > 0)
        }

        return velocities
    }

    fun part2(input: String): Int {
        var parse = input.substringAfter("target area: ")
        val delimitComma = parse.split(",")
        val xString = delimitComma[0].trim().substringAfter("x=")
        val yString = delimitComma[1].trim().substringAfter("y=")

        val (x1str, x2str) = xString.split("..")
        val (y1str, y2str) = yString.split("..")

        val x1 = x1str.toInt()
        val x2 = x2str.toInt()

        val y1 = y1str.toInt()
        val y2 = y2str.toInt()

        val targetArea = TargetArea(x1, x2, y1, y2)

        // Determine the range of x velocities that can possibly reach the target area.

        // Assuming the y velocity at x=0 will be the same as what it was on the way up (except the sign is different)
        // I.e., a y velocity starting at 10 will be -10 by the time it gets back to the x=0 level.
        // To hit the target, the y velocity when it reaches x=0 must not exceed minY.

        var yVelocityRange = targetArea.smallestY()..abs(targetArea.smallestY())

        var xVelocityRange = validXVelocityRange(targetArea, xTransformVelocity)

        println("Y velocities: $yVelocityRange")
        println("X velocities: $xVelocityRange")

        var maxYVelocity = 0
        var highestYPosition = 0

        val velocityCombos = HashSet<Pair<Int, Int>>()

        yVelocityRange.forEach {yVelocity ->
            xVelocityRange.forEach { xVelocity ->
                var x = 0
                var y = 0
                var highestY = 0

                var xCurrentVelocity = xVelocity
                var yCurrentVelocity = yVelocity

                // Given these two velocities, repeat until Y falls below the target area.
                while (!targetArea.isYBelow(y)) {
                    x += xCurrentVelocity
                    y += yCurrentVelocity

                    highestY = highestY.coerceAtLeast(y)

                    if (targetArea.contains(x, y)) {
                        velocityCombos.add(Pair(xVelocity, yVelocity))
                        if (yVelocity > maxYVelocity) {
                            maxYVelocity = yVelocity
                            highestYPosition = highestY
                        }
                    }

                    xCurrentVelocity = xTransformVelocity(xCurrentVelocity)
                    yCurrentVelocity = yTransformVelocity(yCurrentVelocity)
                }
            }
        }

        return velocityCombos.count()
    }

    private val xTransformVelocity: (Int) -> Int = { if (it > 0) it - 1 else 0}

    private val yTransformVelocity: (Int) -> Int = { it - 1 }

    private fun xTransformVelocityFun(xVelocity:Int):Int = if (xVelocity > 0) xVelocity - 1 else 0

    private fun yTransformVelocityFun(yVelocity:Int) { yVelocity - 1 }
}

class TargetArea(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {
    fun contains(x: Int, y: Int): Boolean {
        return containsX(x) && containsY(y)
    }

    fun containsX(x: Int): Boolean {
        return x in x1..x2
    }

    fun containsY(y: Int): Boolean {
        return y in y1..y2
    }

    fun isYBelow(y: Int): Boolean {
        return y < smallestY()
    }

    fun smallestX():Int = x1.coerceAtMost(x2)

    fun largestX():Int = x1.coerceAtLeast(x2)

    fun smallestY():Int = y1.coerceAtMost(y2)

    fun largestY(): Int = y1.coerceAtLeast(y2)
}