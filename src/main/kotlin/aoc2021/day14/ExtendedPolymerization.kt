package aoc2021.day14

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

        return 0
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

data class Node(val value:String, val left:Node?, val right:Node?)