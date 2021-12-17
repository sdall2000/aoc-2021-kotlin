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

    fun part1WithNodes(lines: List<String>, steps: Int): Long {
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

    fun queueBasedNodeTraversal(lines: List<String>, steps: Int): Long {
        val template = lines[0].trim()

        // Associates the character pair with the character that gets inserted between them.
        // CH -> B
        // HH -> N
        // CB -> H
        // etc.
        val pairInsertionRules = HashMap<String, String>()
        lines.forEachIndexed { index, line ->
            if (index >= 2) {
                val (pair, ch) = line.split(" -> ")

                pairInsertionRules[pair] = ch
            }
        }

        // Associates the character pair with a Node object.
        // CH -> Node(value=CH, left=Node, right=Node)
        // HH -> Node(value=HH, left=Node, right=Node)
        // CB -> Node(value=CB, left=Node, right=Node)
        val nodes = HashMap<String, Node>()

        // First, create a node object with no children for each entry.
        // CH -> Node(value=CH, left=null, right=null)
        // HH -> Node(value=HH, left=null, right=null)
        // CB -> Node(value=CB, left=null, right=null)
        pairInsertionRules.keys.forEach { key ->
            nodes[key] = Node(key)
        }

        // Now that each node has been created, set the left/right for each node.
        nodes.forEach { (key, node) ->
            val insertChar = pairInsertionRules[key]!!

            val leftKey = key[0] + insertChar
            val rightKey = insertChar + key[1]

            node.left = nodes[leftKey]
            node.right = nodes[rightKey]
        }

        val charCountMap = HashMap<Char, Long>()

        var lastPair = ""

        val sb = null

        for (index in 0 until template.length - 1) {
            val isLast = index == template.length - 2

            val pair = template.substring(index, index + 2)

            println("Processing pair $pair")
//            val localCharCountMap = printLevel(nodes, pair, steps, cache)
            recursePrintLevel(0, nodes[pair]!!, steps, charCountMap)
            if (isLast) {
                applyCount(charCountMap, pair[1].toString())
//                sb.append(pair[1].toString())
            }
            println()
//            println("String: $sb")
//            sb.clear()
            // Remove the very last character so it's not counted twice.
//            if (lastPair.isNotEmpty()) {
//                val current = charCountMap[lastPair[1]]
//                charCountMap[lastPair[1]] = current!! - 1
//            }

            lastPair = pair
        }

        return (charCountMap.maxOf { it.value} - charCountMap.minOf { it.value }).toLong()
    }

    private fun recursePrintLevel(currentLevel:Int, rootNode:Node, desiredLevel:Int, results:MutableMap<Char, Long>, sb:StringBuilder? = null) {
        if (currentLevel == desiredLevel) {
//            print(" ${rootNode.value}")
            applyCount(results, rootNode.value[0].toString())
            sb?.append(rootNode.value[0])
        } else {
            recursePrintLevel(currentLevel + 1, rootNode.left!!, desiredLevel, results, sb)
            recursePrintLevel(currentLevel + 1, rootNode.right!!, desiredLevel, results, sb)
        }
    }

    private fun printLevel(nodes:Map<String, Node>, root:String, desiredLevel:Int, cache:MutableMap<String, MutableMap<Char, Long>>):MutableMap<Char, Long> {
        var charCountMap = cache[root]
        if (charCountMap != null) {
            println("Cache hit for $root")
            return charCountMap
        } else {
            charCountMap = HashMap()
        }

        val rootNode = nodes[root]!!

        var currentLevel = 0

        var queue = LinkedList<Node>()
        queue.add(rootNode)

        while (!queue.isEmpty()) {

            println("Processing level ${currentLevel + 1}.  Queue has ${queue.size} items")

            if (currentLevel == desiredLevel) {

                queue.forEachIndexed() { index, it ->
//                    print("${it.value} ")
                    if (index == 0) {
                        applyCount(charCountMap, it.value)
                    } else {
                        applyCount(charCountMap, it.value[1].toString())
                    }
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

        cache[root] = charCountMap

        return charCountMap
    }

    private fun applyCount(charCountMap:MutableMap<Char, Long>, value:String) {
        value.forEach {
            if (charCountMap.contains(it)) {
                val count = charCountMap[it]!!
                charCountMap[it] = count + 1
            } else {
                charCountMap[it] = 1
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