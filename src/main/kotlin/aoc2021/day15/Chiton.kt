package aoc2021.day15

import aoc2021.day15.astar.Graph
import aoc2021.day15.astar.RouteFinder

class Chiton {
    fun lowestTotalRisk(lines: List<String>): Int {
        val nodes = HashSet<ChitonNode>()
        val connections = HashMap<String, Set<String>>()

        setNodesAndConnections(lines, nodes, connections)

        val domain = Graph(nodes, connections)
        val routeFinder = RouteFinder(domain, ChiltonNextNodeScorer(), ChiltonHeuristicScorer())

        val maxX = lines[0].length - 1
        val maxY = lines.size - 1

        val startKey = createKey(0, 0)
        val endKey = createKey(maxX, maxY)

        // Use A-Star algorithm.  Ported to Kotlin from here: https://www.baeldung.com/java-a-star-pathfinding
        val route = routeFinder.findRoute(domain.getNode(startKey), domain.getNode(endKey))

        return route.sumOf {
            it.risk
        } - domain.getNode(startKey).risk
    }

    fun lowestTotalRisk5x(lines: List<String>): Int {

        val newLines = modifyInput(lines)

        val nodes = HashSet<ChitonNode>()
        val connections = HashMap<String, Set<String>>()

        setNodesAndConnections(newLines, nodes, connections)

        val domain = Graph(nodes, connections)
        val routeFinder = RouteFinder(domain, ChiltonNextNodeScorer(), ChiltonHeuristicScorer())

        val maxX = newLines[0].length - 1
        val maxY = newLines.size - 1

        val startKey = createKey(0, 0)
        val endKey = createKey(maxX, maxY)

        val route = routeFinder.findRoute(domain.getNode(startKey), domain.getNode(endKey))

        return route.sumOf {
            it.risk
        } - domain.getNode(startKey).risk
    }

    private fun modifyInput(lines: List<String>): List<String> {
        val newLines = ArrayList<String>()

        lines.forEach { line ->
            val intArray = lineToIntArray(line)
            val sb = StringBuilder()

            repeat(5) {
                sb.append(intArray.joinToString(""))
                intArray.forEachIndexed { index, number ->
                    var newNumber = number + 1
                    if (newNumber == 10) {
                        newNumber = 1
                    }
                    intArray[index] = newNumber
                }
            }

            newLines.add(sb.toString())
        }

        // Now, the width of the lines are adjusted to be 5x.
        // We need to do the same for the number of lines.

        val expandedWidthListOfIntArray = mutableListOf<IntArray>()

        newLines.forEach { line ->
            expandedWidthListOfIntArray.add(lineToIntArray(line))
        }

        val newNewLines = mutableListOf<String>()

        StringBuilder()

        repeat(5) {
            expandedWidthListOfIntArray.forEach {
                newNewLines.add(it.joinToString(""))
            }

            expandedWidthListOfIntArray.forEach {
                it.forEachIndexed { index, number ->
                    var newNumber = number + 1
                    if (newNumber == 10) {
                        newNumber = 1
                    }
                    it[index] = newNumber
                }
            }
        }

        return newNewLines
    }

    private fun lineToIntArray(line: String): IntArray {
        val intArray = IntArray(line.length)

        line.forEachIndexed { index, ch ->
            intArray[index] = ch.digitToInt()
        }

        return intArray
    }

    private fun setNodesAndConnections(
        lines: List<String>,
        nodes: MutableSet<ChitonNode>,
        connections: MutableMap<String, Set<String>>
    ) {
        val rows = lines.size
        val cols = lines[0].length

        val maxY = rows - 1
        val maxX = cols - 1

        lines.forEachIndexed { y, line ->
            IntArray(cols)

            line.forEachIndexed { x, chr ->
                val risk = chr.digitToInt()

                val nodeKey = createKey(x, y)

                val chitonNode = ChitonNode(nodeKey, x, y, risk)
                nodes.add(chitonNode)

                // Since this is a square grid, we can construct the neighbor keys.
                val north = intArrayOf(x, y - 1)
                val east = intArrayOf(x + 1, y)
                val south = intArrayOf(x, y + 1)
                val west = intArrayOf(x - 1, y)

                val candidateNeighbors = arrayOf(north, south, east, west)

                val neighbors = mutableSetOf<String>()

                candidateNeighbors.forEach {
                    if (isValidXY(maxX, maxY, it[0], it[1])) {
                        neighbors.add(createKey(it[0], it[1]))
                    }
                }

                connections[nodeKey] = neighbors
            }
        }
    }

    private fun isValidXY(maxX: Int, maxY: Int, x: Int, y: Int): Boolean {
        return x in 0..maxX && y in 0..maxY
    }

    private fun createKey(x: Int, y: Int): String = "$x/$y"
}