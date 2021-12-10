package aoc2021.day10

import java.util.*

class SyntaxScoring {
    fun getSyntaxErrorScore(lines: List<String>): Int {
        val charPairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
        val scoreMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

        var score = 0

        lines.forEach { line ->
            val stack = Stack<Char>()
            // Use for loop instead of forEach so we can use break.
            for (c in line) {
                if (c in charPairs.keys) {
                    stack.push(c)
                } else {
                    val popped = stack.pop()

                    val closeChar = charPairs[popped]

                    if (c != closeChar) {
                        score += scoreMap[c]!!
                    }
                }
            }
        }

        return score
    }

    fun getMiddleScore(lines: List<String>): Long {
        val charPairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
        val scoreMap = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

        val scores = ArrayList<Long>()

        lines.forEach { line ->
            val stack = Stack<Char>()

            // Use for loop instead of forEach so we can use break.
            for (c in line) {
                if (c in charPairs.keys) {
                    stack.push(c)
                } else {
                    val popped = stack.pop()

                    val closeChar = charPairs[popped]

                    if (c != closeChar) {
                        // Bad line that we should ignore.
                        // Clear out the stack so no score is calculated, and break out of loop.
                        stack.clear()
                        break
                    }
                }
            }

            if (!stack.isEmpty()) {
                var localScore: Long = 0

                while (!stack.isEmpty()) {
                    val c = stack.pop()
                    val closeChar = charPairs[c]

                    localScore *= 5
                    localScore += scoreMap[closeChar]!!
                }

                scores.add(localScore)
            }
        }

        // Get the middle element.  Requirements say there will always be an odd number of scores.
        // So, if there are five elements, we want the zero based index at two.
        return scores.sorted().get(scores.size / 2)
    }
}