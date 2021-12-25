package aoc2021.day25

class SeaCucumber {
    private val southHerd = 'v'
    private val eastHerd = '>'
    private val empty = '.'

    fun part1(lines: List<String>): Long {

        var map = Array(lines.size) { charArrayOf() }

        lines.forEachIndexed { lineIndex, line ->
            map[lineIndex] = line.toCharArray()
        }

        var steps = 0L

        var steadyState = false

        while (!steadyState) {
            steadyState = true
            steps++

            // Create an empty map to move the east herd.
            var newMap = Array(map.size) { CharArray(map[0].size) { '.' }}

            map.forEachIndexed { y, charArray ->
                charArray.forEachIndexed { x, char ->
                    if (char == eastHerd) {
                        val nextX = nextEastPosition(x, map)
                        if (isPositionOpen(nextX, y, map)) {
                            newMap[y][nextX] = eastHerd
                            steadyState = false
                        } else {
                            newMap[y][x] = eastHerd
                        }
                    } else if (char == southHerd) {
                        newMap[y][x] = southHerd
                    }
                }
            }


            map = newMap

            newMap = Array(map.size) { CharArray(map[0].size) { '.' }}

            map.forEachIndexed { y, charArray ->
                charArray.forEachIndexed { x, char ->
                    if (char == southHerd) {
                        val nextY = nextSouthPosition(y, map)
                        if (isPositionOpen(x, nextY, map)) {
                            newMap[nextY][x] = southHerd
                            steadyState = false
                        } else {
                            newMap[y][x] = southHerd
                        }
                    } else if (char == eastHerd) {
                        newMap[y][x] = eastHerd
                    }
                }
            }

            map = newMap
        }

        return steps
    }

    private fun nextEastPosition(x: Int, map: Array<CharArray>): Int {
        return if (x == map[0].size - 1) {
            0
        } else {
            x + 1
        }
    }

    private fun nextSouthPosition(y: Int, map: Array<CharArray>): Int {
        return if (y == map.size - 1) {
            0
        } else {
            y + 1
        }
    }

    private fun isPositionOpen(x: Int, y: Int, map: Array<CharArray>): Boolean {
        return map[y][x] == empty
    }
}