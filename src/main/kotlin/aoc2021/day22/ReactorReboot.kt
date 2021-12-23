package aoc2021.day22

import kotlin.math.abs

class ReactorReboot {
    fun countOnCubes(lines: List<String>, maxValue: Int? = null): Long {
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

    fun countOnCubes2(lines: List<String>, maxValue: Int? = null): Long {
        val coordinateSet = mutableSetOf<String>()

        var onCubes = mutableListOf<Cube>()
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

        // First, rebuild the on cubes so there are no more intersections.
        println("On cube original size=${onCubes.size}")

        var done = false

        while (!done) {
            val cubes = mutableListOf<Cube>()
            cubes.addAll(onCubes)

            done = true

            onCubes.forEach { onCube ->
                onCubes.filter { onCube2 ->
                    onCube != onCube2 && onCube.intersects(onCube2)
                }.forEach { onCube3 ->
                    if (done) {
                        if (!cubes.remove(onCube)) {
                            println("Failed to remove cube $onCube")
                        }
                        val newCubes = onCube.remove(onCube3)
                        if (newCubes.isNotEmpty()) {
                            cubes.addAll(newCubes)
                            println("Updated on cubes size: ${cubes.size}")

                            println("originalCube: $onCube")
                            println("intersectingCube: $onCube3")
                            println("intersection: ${onCube.intersect(onCube3)}")
                            println("New cubes: ")
                            newCubes.forEach { c ->
                                println("  $c")
                            }
                        }
                        done = false
                    }
                }
            }

            if (!done) {
                onCubes = cubes
            }
        }

        println("On cube new size=${onCubes.size}")
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

    private fun inRange(maxValue: Int, number: IntArray): Boolean {
        number.forEach {
            if (abs(it) > maxValue) {
                return false
            }
        }
        return true
    }
}

data class Cube(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int, val minZ: Int, val maxZ: Int) {
    fun size(): Int = (maxX - minX) * (maxY - minY) * (maxZ - minZ)

    // Returns true if the other cube is inside this cube.  matching edges count as well.
    fun contains(other: Cube): Boolean {
        return other.minX in minX..maxX && other.maxX in minX..maxX &&
                other.minY in minY..maxY && other.maxY in minY..maxY &&
                other.minZ in minZ..maxZ && other.maxZ in minZ..maxZ
    }

    // Returns true if the other cube intersects this cube.  Edges that touch do not count.
    fun intersects(other: Cube): Boolean {
        return other.minX <= maxX && other.maxX >= minX &&
                other.minY <= maxY && other.maxY >= minY &&
                other.minZ <= maxZ && other.maxZ >= minZ
    }

    fun intersect(other: Cube): Cube? {
        var intersection: Cube? = null

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

    // Returns one or more cubes after removing the given cube
    // May return an empty list if the cube passed in as a parameter contains this cube object.
    fun remove(cube: Cube): List<Cube> {
        val cubeList = mutableListOf<Cube>()

        // First get the intersection
        val intersectCube = intersect(cube)

        // See if an intersection exists
        if (intersectCube != null) {
            // Now, we need to subtract the intersect cube from this cube.
            // Many use cases:
            //    The intersect cube contains this cube.  So no cube is left and we will return an empty list.
            //

            // Create up to six new cubes.

            // Default to current cube values.
            var newMinY = minY
            var newMaxY = maxY
            var newMinX = minX
            var newMaxX = maxX

            // This is the area above the intersection.
            if (intersectCube.maxY < maxY) {
                cubeList.add(Cube(minX, maxX, intersectCube.maxY, maxY, minZ, maxZ))
                newMaxY = intersectCube.maxY
            }

            // Area below the intersection
            if (intersectCube.minY > minY) {
                cubeList.add(Cube(minX, maxX, minY, intersectCube.minY, minZ, maxZ))
                newMinY = intersectCube.minY
            }

            // Area to the right of the intersection
            if (intersectCube.maxX < maxX) {
                cubeList.add(Cube(intersectCube.maxX, maxX, newMinY, newMaxY, minZ, maxZ))
                newMaxX = intersectCube.maxX
            }

            // Area to the left of the intersection
            if (intersectCube.minX > minX) {
                cubeList.add(Cube(minX, intersectCube.minX, newMinY, newMaxY, minZ, maxZ))
                newMinX = intersectCube.minX
            }

            if (intersectCube.maxZ < maxZ) {
                cubeList.add(Cube(newMinX, newMaxX, newMinY, newMaxY, intersectCube.maxZ, maxZ))
            }

            if (intersectCube.minZ > minZ) {
                cubeList.add(Cube(newMinX, newMaxX, newMinY, newMaxY, minZ, intersectCube.minZ))
            }
        } else {
            // There is no intersection.  Return a copy of this cube.
            // (Could we just add "this" instead of a copy?)
            cubeList.add(this.copy())
        }

        return cubeList
    }
}