package aoc2021.day22

import kotlin.math.abs

class ReactorReboot {
    fun countOnCubes(lines:List<String>, maxValue:Int? = null): Long {
        val coordinateSet = mutableSetOf<String>()

        lines.forEach { line ->
            val turnOn = line.startsWith("on")

            val onOffStr = if (turnOn) "on " else "off "

            val (xStr, yStr, zStr) = line.substringAfter(onOffStr).split(",")

            val (minX, maxX) = buildRange(xStr.substringAfter("x="))
            val (minY, maxY) = buildRange(yStr.substringAfter("y="))
            val (minZ, maxZ) = buildRange(zStr.substringAfter("z="))

            if (maxValue == null || inRange(maxValue, intArrayOf(minX, maxX, minY, maxY, minZ, maxZ))) {
                for (x in minX..maxX) {
                    for (y in minY..maxY) {
                        for (z in minZ..maxZ) {
                            val key = buildPositionKey(x, y, z)
                            if (turnOn) {
                                coordinateSet.add(key)
                            } else {
                                coordinateSet.remove(key)
                            }
                        }
                    }
                }
            }
        }

        return coordinateSet.size.toLong()
    }

    fun countOnCubes2(lines:List<String>, maxValue:Int? = null): Long {
        val coordinateSet = mutableSetOf<String>()

        val onCubes = mutableListOf<Cube>()
        val offCubes = mutableListOf<Cube>()

        lines.forEach { line ->
            val turnOn = line.startsWith("on")

            val onOffStr = if (turnOn) "on " else "off "

            val (xStr, yStr, zStr) = line.substringAfter(onOffStr).split(",")

            val (minX, maxX) = buildRange(xStr.substringAfter("x="))
            val (minY, maxY) = buildRange(yStr.substringAfter("y="))
            val (minZ, maxZ) = buildRange(zStr.substringAfter("z="))

            val cube = Cube(minX, maxX, minY, maxY, minZ, maxZ)

            if (turnOn) {
                onCubes.add(cube)
            } else {
                offCubes.add(cube)
            }
        }

        val maxOnCube = onCubes.maxByOrNull {
            it.size()
        }

        val maxOffCube = offCubes.maxByOrNull {
            it.size()
        }

        onCubes.forEach {
            if (it != maxOnCube) {
                if (maxOnCube!!.intersects(it)) {
                    println("On cube $maxOnCube intersects cube $it.  Intersection is ${maxOnCube.intersect(it)}")
                }

                if (maxOffCube!!.intersects(it)) {
                    println("Off cube $maxOffCube intersects cube $it.  Intersection is ${maxOffCube.intersect(it)}")
                }
            }
        }

//        offCubes.forEach {
//            if (it != maxOffCube) {
//                if (maxOffCube!!.intersects(it)) {
//                    println("Off cube $maxOffCube intersects cube $it.  Intersection is ${maxOffCube.intersect(it)}")
//                }
//
//                if (maxOnCube!!.intersects(it)) {
//                    println("On cube $maxOnCube intersects cube $it.  Intersection is ${maxOnCube.intersect(it)}")
//                }
//            }
//        }

        return coordinateSet.size.toLong()
    }

    // Ensures that the first element is less than the second element
    private fun buildRange(rangeStr: String): Pair<Int, Int> {
        val (num1, num2) = rangeStr.split("""..""")

        val num1Val = num1.toInt()
        val num2Val = num2.toInt()

        if (num1Val < num2Val) {
            return Pair(num1Val, num2Val)
        } else {
            return Pair(num2Val, num1Val)
        }
    }

    private fun buildPositionKey(x: Int, y: Int, z: Int): String {
        return "$x/$y/$z"
    }

    private fun inRange(maxValue: Int, number:IntArray): Boolean {
        number.forEach {
            if (abs(it) > maxValue) {
                return false
            }
        }
        return true
    }
}

data class Cube(val minX:Int, val maxX: Int, val minY: Int, val maxY: Int, val minZ: Int, val maxZ: Int) {
    fun size():Int = (maxX - minX) * (maxY - minY) * (maxZ - minZ)

    // Returns true if the other cube is inside this cube.  matching edges count as well.
    fun contains(other:Cube): Boolean {
        return other.minX in minX..maxX && other.maxX in minX..maxX &&
                other.minY in minY..maxY && other.maxY in minY..maxY &&
                other.minZ in minZ..maxZ && other.maxZ in minZ..maxZ
    }

    // Returns true if the other cube intersects this cube.  Edges that touch do not count.
    fun intersects(other:Cube): Boolean {
        return other.minX < maxX && other.maxX > minX &&
                other.minY < maxY && other.maxY > minY &&
                other.minZ < maxZ && other.maxZ > minZ
    }

    fun intersect(other:Cube): Cube? {
        var intersection:Cube? = null

        if (intersects(other)) {
            // Get the maximum min value
            val newMinX = minX.coerceAtLeast(other.minX)
            val newMinY = minY.coerceAtLeast(other.minY)
            val newMinZ = minZ.coerceAtLeast(other.minZ)

            // Get the minimum max value
            val newMaxX = maxX.coerceAtMost(other.maxX)
            val newMaxY = maxY.coerceAtMost(other.maxY)
            val newMaxZ = maxZ.coerceAtMost(other.maxZ)

            intersection = Cube(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ)
        }

        return intersection
    }
}