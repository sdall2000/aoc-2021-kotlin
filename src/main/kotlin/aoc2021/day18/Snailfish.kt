package aoc2021.day18

import java.util.concurrent.atomic.AtomicBoolean

class Snailfish {
    fun part1(lines: List<String>): Long {
        var currentSnailPair: SnailPair? = null

        lines.forEach {
            val snailPair = SnailPair.parse(it)

            currentSnailPair = if (currentSnailPair == null) {
                snailPair
            } else {
                currentSnailPair!! + snailPair!!
            }
        }

        return currentSnailPair!!.magnitude()
    }

    fun part2(lines: List<String>): Long {
        var largestSum:Long = 0

        lines.forEach { firstLine ->
            val secondLines = ArrayList<String>(lines)
            secondLines.remove(firstLine)

            secondLines.forEach {secondLine ->
                val sum1 = (SnailPair.parse(firstLine)!! + SnailPair.parse(secondLine)!!).magnitude()
                val sum2 = (SnailPair.parse(secondLine)!! + SnailPair.parse(firstLine)!!).magnitude()

                largestSum = largestSum.coerceAtLeast(sum1.coerceAtLeast(sum2))
            }
        }

        return largestSum
    }
}

class SnailPair(
    var left: SnailPair? = null,
    var right: SnailPair? = null,
    var value: Long? = null
) {
    operator fun plus(other: SnailPair): SnailPair {
        val snailPair = SnailPair(this, other)
        snailPair.reduce()
        return snailPair
    }

    fun reduce() {
        var done = false

        while (!done) {
            val exploded = doReduceExplode(this, null, this, 1)

            var split = false

            if (!exploded) {
                split = doReduceSplit(this)
            }

            done = !exploded && !split
        }
    }

    fun magnitude(): Long {
        return doMagnitude(this)
    }

    fun doMagnitude(snailPair: SnailPair?): Long {
        if (snailPair == null) return 0

        if (snailPair.value != null) {
            return snailPair.value!!
        }

        return 3 * snailPair.left!!.magnitude() + 2 * snailPair.right!!.magnitude()
    }

    fun addValueToNodeAfter(
        startValueNode: SnailPair,
        currentNode: SnailPair?,
        nodeFound: AtomicBoolean,
        nodeSet: AtomicBoolean
    ) {
        if (currentNode == null || nodeSet.get()) {
            return
        }

        if (nodeFound.get()) {
            if (currentNode.value != null) {
                currentNode.value = currentNode.value!! + startValueNode.value!!
                nodeSet.set(true)
            } else {
                addValueToNodeAfter(startValueNode, currentNode.left, nodeFound, nodeSet)
                addValueToNodeAfter(startValueNode, currentNode.right, nodeFound, nodeSet)
            }
        } else {
            if (currentNode == startValueNode) {
                nodeFound.set(true)
            } else {
                addValueToNodeAfter(startValueNode, currentNode.left, nodeFound, nodeSet)
                addValueToNodeAfter(startValueNode, currentNode.right, nodeFound, nodeSet)
            }
        }
    }

    fun addValueToNodeBefore(
        startValueNode: SnailPair,
        currentNode: SnailPair?,
        nodeFound: AtomicBoolean,
        nodeSet: AtomicBoolean
    ) {
        if (currentNode == null || nodeSet.get()) {
            return
        }

        if (nodeFound.get()) {
            if (currentNode.value != null) {
                currentNode.value = currentNode.value!! + startValueNode.value!!
                nodeSet.set(true)
            } else {
                addValueToNodeBefore(startValueNode, currentNode.right, nodeFound, nodeSet)
                addValueToNodeBefore(startValueNode, currentNode.left, nodeFound, nodeSet)
            }
        } else {
            if (currentNode == startValueNode) {
                nodeFound.set(true)
            } else {
                addValueToNodeBefore(startValueNode, currentNode.right, nodeFound, nodeSet)
                addValueToNodeBefore(startValueNode, currentNode.left, nodeFound, nodeSet)
            }
        }
    }

    private fun doReduceExplode(
        snailPair: SnailPair?,
        parent: SnailPair?,
        root: SnailPair,
        level: Int
    ): Boolean {
        if (snailPair == null) return false

        if (snailPair.value == null) {
            if (level >= 5) {
                val nodeFound = AtomicBoolean(false)
                val nodeSet = AtomicBoolean(false)
                root.addValueToNodeAfter(snailPair.right!!, root, nodeFound, nodeSet)

                nodeFound.set(false)
                nodeSet.set(false)

                root.addValueToNodeBefore(snailPair.left!!, root, nodeFound, nodeSet)

                parent!!.zeroMe(snailPair)

                return true
            }
            if (doReduceExplode(snailPair.left, snailPair, root, level + 1)) return true
            if (doReduceExplode(snailPair.right, snailPair, root, level + 1)) return true
        }

        return false
    }

    private fun doReduceSplit(
        snailPair: SnailPair?
    ): Boolean {
        if (snailPair == null) return false

        if (snailPair.value != null && snailPair.value!! >= 10) {
            val leftValue = snailPair.value!! / 2
            val rightValue = snailPair.value!! - leftValue

            snailPair.value = null
            snailPair.left = SnailPair(value = leftValue)
            snailPair.right = SnailPair(value = rightValue)

            return true
        }

        if (doReduceSplit(snailPair.left)) return true
        if (doReduceSplit(snailPair.right)) return true

        return false
    }

    private fun zeroMe(childSnailPair: SnailPair) {
        if (left == childSnailPair) {
            left = SnailPair(value = 0)
        } else if (right == childSnailPair) {
            right = SnailPair(value = 0)
        } else {
            println("zeroMe could not find child node")
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()

        traverseBuildString(this, sb)

        return sb.toString()
    }

    private fun traverseBuildString(snailPair: SnailPair?, sb: StringBuilder) {
        if (snailPair == null) return

        if (snailPair.value != null) {
            sb.append(snailPair.value.toString())
        } else {
            sb.append("[")
            traverseBuildString(snailPair.left, sb)
            sb.append(",")
            traverseBuildString(snailPair.right, sb)
            sb.append("]")
        }
    }

    companion object {
        fun parse(line: String): SnailPair? {
            // Probably should never happen.
            if (line.isEmpty()) {
                println("Line was empty")
                return null
            }

            val snailPair: SnailPair?

            if (line.contains("[")) {
                // How to find the correct comma?
                // Let's strip off the outer []
                val str = line.substring(1, line.length - 1)

                // Now, scan for a comma that is not preceeded by a [
                var openBraceCount = 0
                var commaIndex = 0

                str.forEachIndexed { ind, chr ->
                    if (chr == ',' && openBraceCount == 0) {
                        commaIndex = ind
                    } else if (chr == '[') {
                        openBraceCount++
                    } else if (chr == ']') {
                        openBraceCount--
                    }
                }

                val left = str.substring(0, commaIndex)
                val right = str.substring(commaIndex + 1)

                snailPair = SnailPair(left = parse(left), right = parse(right))
            } else if (line.contains(",")) {
                // Should be two values now.
                val split = line.split(",")

                snailPair = SnailPair(left = parse(split[0]), right = parse(split[1]))
            } else {
                // Just a value
                return SnailPair(value = line.toLong())
            }

            return snailPair
        }
    }
}