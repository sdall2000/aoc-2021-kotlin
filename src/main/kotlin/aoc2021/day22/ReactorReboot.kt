package aoc2021.day22

import kotlin.math.abs

class ReactorReboot {
    fun countOnCubes(lines: List<String>, maxValue: Long? = null): Long {
        var cubes = mutableListOf<Cube>()

        lines.forEachIndexed { lineIndex, line ->
                val turnOn = line.startsWith("on")

                val onOffStr = if (turnOn) "on " else "off "

                val (xStr, yStr, zStr) = line.substringAfter(onOffStr).split(",")

                val (minX, maxX) = buildRange(xStr.substringAfter("x="))
                val (minY, maxY) = buildRange(yStr.substringAfter("y="))
                val (minZ, maxZ) = buildRange(zStr.substringAfter("z="))

                if (minX > maxX || minY > maxY || minZ > maxZ) {
                    println("Nope")
                }

                if (maxValue == null || inRange(maxValue, longArrayOf(minX, maxX, minY, maxY, minZ, maxZ))) {
                    val cube = Cube(minX, maxX, minY, maxY, minZ, maxZ)

                    if (turnOn) {
                        cubes = union(cubes, cube)
                    } else {
                        cubes = subtract(cubes, cube)
                    }
                }
        }

        return cubes.sumOf { cube ->
            cube.cubeCount()
        }
    }

    private fun union(cubesIn: MutableList<Cube>, cube: Cube): MutableList<Cube> {
        var done = false

        var outCubes = mutableListOf<Cube>()
        outCubes.addAll(cubesIn)

        val cubesAltered = mutableSetOf<String>()

        while (!done) {
            val cubes = mutableListOf<Cube>()
            cubes.addAll(outCubes)

            done = true

            for (thisCubeIndex in outCubes.indices) {
                if (done) {
                    val thisCube = outCubes[thisCubeIndex]

                    if (thisCube.intersects(cube)) {
                        val newCubes = thisCube.remove(cube)

                        if (!cubes.remove(thisCube)) {
                            println("Failed to remove cube $thisCube")
                        }

                        cubes.addAll(newCubes)
//                            println("Updated on cubes size: ${cubes.size}")

                        val cubeString = thisCube.toString()

                        if (cubesAltered.contains(cubeString)) {
                            println("Whaaaaaa $thisCube")
                        } else {
                            cubesAltered.add(cubeString)
                        }

//                            println("originalCube: $onCube")
//                            println("intersectingCube: $onCube3")
//                            println("intersection: ${onCube.intersect(onCube3)}")
//                            println("New cubes: ")
//                                    newCubes.forEach { c ->
//                                println("  $c")
//                                    }
                        done = false
                    }
                }
            }

            if (!done) {
                outCubes = cubes
            }
        }

        outCubes.add(cube)

        return outCubes
    }

    private fun subtract(cubesIn: MutableList<Cube>, cube: Cube): MutableList<Cube> {
        var done = false

        val cubesAltered = mutableSetOf<String>()

        var onCubes = mutableListOf<Cube>()
        onCubes.addAll(cubesIn)

        while (!done) {
            val cubes = mutableListOf<Cube>()
            cubes.addAll(onCubes)

            done = true

            for (thisCubeIndex in onCubes.indices) {
                if (done) {
                    val thisCube = onCubes[thisCubeIndex]
                    val offCube = cube

                    if (thisCube.intersects(offCube)) {
                        val newCubes = thisCube.remove(offCube)

//                        if (newCubes.isNotEmpty()) {
                        if (!cubes.remove(thisCube)) {
                            println("Failed to remove cube $thisCube")
                        }

                        cubes.addAll(newCubes)
//                            println("Updated on cubes size: ${cubes.size}")

                        val cubeString = thisCube.toString()

                        if (cubesAltered.contains(cubeString)) {
                            println("Whaaaaaa $thisCube")
                        } else {
                            cubesAltered.add(cubeString)
                        }

//                            println("originalCube: $onCube")
//                            println("intersectingCube: $onCube3")
//                            println("intersection: ${onCube.intersect(onCube3)}")
//                            println("New cubes: ")
//                                    newCubes.forEach { c ->
//                                println("  $c")
//                                    }
                        done = false
//                        }
                    }
                }
            }

            if (!done) {
                onCubes = cubes
            }
        }

        return onCubes
    }

    // Ensures that the first element is less than the second element
    private fun buildRange(rangeStr: String): Pair<Long, Long> {
        val (num1, num2) = rangeStr.split("""..""")

        val num1Val = num1.toLong()
        val num2Val = num2.toLong()

        if (num1Val < num2Val) {
            return Pair(num1Val, num2Val)
        } else {
            return Pair(num2Val, num1Val)
        }
    }

    private fun inRange(maxValue: Long, number: LongArray): Boolean {
        number.forEach {
            if (abs(it) > maxValue) {
                return false
            }
        }
        return true
    }
}

// Note that when we count the number of cubes contained in this cube, we are not calculating an area.
// Rather, there is a single cube at each coordinate position.  So a cube with coordinates (0,1), (0,1), (0,1) (x/y/z)
// does not have one cube.  It is actually eight.
data class Cube(val minX: Long, val maxX: Long, val minY: Long, val maxY: Long, val minZ: Long, val maxZ: Long) {
    fun cubeCount(): Long = (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)

    // Returns true if the other cube is inside this cube.  matching edges count as well.
    fun contains(other: Cube): Boolean {
        return other.minX in minX..maxX && other.maxX in minX..maxX &&
                other.minY in minY..maxY && other.maxY in minY..maxY &&
                other.minZ in minZ..maxZ && other.maxZ in minZ..maxZ
    }

    // Returns true if the other cube intersects this cube.  Edges that touch do count.
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
                cubeList.add(Cube(minX, maxX, intersectCube.maxY + 1, maxY, minZ, maxZ))
                newMaxY = intersectCube.maxY
            }

            // Area below the intersection
            if (intersectCube.minY > minY) {
                cubeList.add(Cube(minX, maxX, minY, intersectCube.minY - 1, minZ, maxZ))
                newMinY = intersectCube.minY
            }

            // Area to the right of the intersection
            if (intersectCube.maxX < maxX) {
                cubeList.add(Cube(intersectCube.maxX + 1, maxX, newMinY, newMaxY, minZ, maxZ))
                newMaxX = intersectCube.maxX
            }

            // Area to the left of the intersection
            if (intersectCube.minX > minX) {
                cubeList.add(Cube(minX, intersectCube.minX - 1, newMinY, newMaxY, minZ, maxZ))
                newMinX = intersectCube.minX
            }

            // Area in front of the intersection
            if (intersectCube.maxZ < maxZ) {
                cubeList.add(Cube(newMinX, newMaxX, newMinY, newMaxY, intersectCube.maxZ + 1, maxZ))
            }

            // Area behind the intersection
            if (intersectCube.minZ > minZ) {
                cubeList.add(Cube(newMinX, newMaxX, newMinY, newMaxY, minZ, intersectCube.minZ - 1))
            }
        } else {
            // There is no intersection, so this cube remains intact.
            println("Cube had no intersection in minus")
            cubeList.add(this)
        }

        return cubeList
    }
}