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

data class Cube(val minX:Long, val maxX: Long, val minY: Long, val maxY: Long, val minZ: Long, val maxZ: Long) {
    fun size():Long = (maxX - minX) * (maxY - minY) * (maxZ - minZ)
}