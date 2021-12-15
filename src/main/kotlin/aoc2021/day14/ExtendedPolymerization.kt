package aoc2021.day14

import java.util.*
import kotlin.collections.HashMap

class ExtendedPolymerization {
    fun part1(lines: List<String>, steps: Int): Long {
        val template = lines[0].trim()

        val pairInsertionRules = HashMap<String, String>()

        lines.forEachIndexed { index, line ->
            if (index >= 2) {
                val (pair, ch) = line.split(" -> ")

                pairInsertionRules[pair] = ch
            }
        }

        var sb = StringBuilder(template)

        println("Start: $template")

        repeat(steps) {
            val newSb = StringBuilder()

            var first = true

            for (index in 0 until sb.length - 1) {
                val pair = sb.substring(index, index + 2)
                val insert = pairInsertionRules[pair]

                if (insert != null) {
                    if (first) {
                        newSb.append(pair[0] + insert + pair[1])
                        first = false
                    } else {
                        newSb.append(insert + pair[1])
                    }
                }
            }

            println("Iteration ${it + 1}: $newSb")

            sb = newSb

//            println("After step $it: $sb")
        }

        val counts = HashMap<Char, Long>()

        sb.forEach {
            if (counts.contains(it)) {
                val v = counts[it]!!
                counts[it] = v + 1
            } else {
                counts[it] = 1
            }
        }

        val max = counts.values.maxOf { it }
        val min = counts.values.minOf { it }


        // Not 20

        return max - min
    }

    fun part2New(lines: List<String>, steps: Int): Long {
        val template = lines[0].trim()

        val pairInsertionRules = HashMap<String, String>()

        lines.forEachIndexed { index, line ->
            if (index >= 2) {
                val (pair, ch) = line.split(" -> ")

                pairInsertionRules[pair] = ch
            }
        }

        val pairTree = HashMap<String, Pair<String, String>>()

        pairInsertionRules.forEach { v, c ->
            val node = pairTree[v]

            val left = v[0] + c
            val right = c + v[1]

            if (node == null) {
                pairTree[v] = Pair(left, right)
            }
        }

        pairTree.forEach { t, u ->
            println("Node $t has pair $u")
        }

        // Now, build up the nodes.
        val nodes = HashMap<String, Node>()

        // First, create a node object with no children for each entry.
        pairTree.keys.forEach { key ->
            nodes[key] = Node(key)
        }

        // Now, set the left/right for each node.
        nodes.forEach { (key, node) ->
            val insertChar = pairInsertionRules[key]!!

            val leftKey = key[0] + insertChar
            val rightKey = insertChar + key[1]

            node.left = nodes[leftKey]
            node.right = nodes[rightKey]
        }

        printLevel(nodes, nodes.keys.first(), 4)

        return 0
    }

    fun printLevel(nodes:Map<String, Node>, root:String, desiredLevel:Int) {
        val rootNode = nodes[root]!!

        var currentLevel = 1

        var queue = LinkedList<Node>()
        queue.add(rootNode)

        while (!queue.isEmpty()) {
            if (currentLevel == desiredLevel) {
                queue.forEach {
                    print("${it.value} ")
                }
                queue.clear()
            } else {
                val newQueue = LinkedList<Node>()

                queue.forEach {
                    newQueue.add(it.left!!)
                    newQueue.add(it.right!!)
                }

                currentLevel++

                queue = newQueue
            }
        }
    }

//    val counts = HashMap<Char, Long>()
//
//    sb.forEach
//    {
//        if (counts.contains(it)) {
//            val v = counts[it]!!
//            counts[it] = v + 1
//        } else {
//            counts[it] = 1
//        }
//    }
//
//    val max = counts.values.maxOf { it }
//    val min = counts.values.minOf { it }
//
//    return max - min
//}

    private fun buildMapWithCounts(lineTwoChars: String, mappings: Map<String, String>, iterations: Int): Map<Char, Int> {
        val countMap = HashMap<Char, Int>()

        var sb = StringBuilder(lineTwoChars)

        repeat(iterations) {
            val newSb = StringBuilder()

            var first = true

            for (index in 0 until sb.length - 1) {
                val pair = sb.substring(index, index + 2)
                val insert = mappings[pair]

                if (insert != null) {
                    if (first) {
                        newSb.append(pair[0] + insert + pair[1])
                        first = false
                    } else {
                        newSb.append(insert + pair[1])
                    }
                }
            }

            sb = newSb
        }

        return countMap
    }

}

data class Node(val value:String, var left:Node? = null, var right:Node? = null)