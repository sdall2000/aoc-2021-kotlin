package aoc2021.day8

import kotlin.math.pow

class SevenSegment {
    fun part1(lines: List<String>): Int {
        // We are searching for segment counts that go with the numbers 1, 4, 7, 8.  Those numbers correspond to counts
        // 2, 4, 3, and 7.  Create an array of the unique segment counts so it can be used as a filter.
        val uniqueSegmentCounts = intArrayOf(2, 4, 3, 7)

        return lines.sumOf { line ->
            val output = line.split(" | ")[1]

            val outputs = output.split(" ")

            outputs.count {uniqueSegmentCounts.contains(it.length)}
        }
    }

    fun part2(lines: List<String>): Int {
        var sum = 0

        lines.forEach { line ->
            val (signal, output) = line.split(" | ")

            val signals = signal.split(" ")
            val outputs = output.split(" ")

            // Map the encoded segment string to the plaintext digit.
            // Note the segment string must be sorted
            val encodedToNumberMap = HashMap<String, Int>()

            val signalsRemaining = signals.toMutableSet()

            // Store the encoded segments for numbers one and four.  Those correspond to segment counts of 2 and 4.
            // They will be used later to test intersections against the other sequences.
            var numberOneCipherSegments:Set<Char> = emptySet()
            var numberFourCipherSegments:Set<Char> = emptySet()

            // Solve ciphertext mappings to unique length numbers.
            signals.forEach {s ->
                val encoded = sortString(s)
                // This is how many segments
                when (encoded.length) {
                    2 -> {
                        encodedToNumberMap[encoded] = 1
                        numberOneCipherSegments = encoded.toCharArray().toSet()

                        signalsRemaining.remove(s)
                    }
                    3 -> {
                        encodedToNumberMap[encoded] = 7
                        signalsRemaining.remove(s)
                    }
                    4 -> {
                        encodedToNumberMap[encoded] = 4
                        numberFourCipherSegments = encoded.toCharArray().toSet()

                        signalsRemaining.remove(s)
                    }
                    7 -> {
                        encodedToNumberMap[encoded] = 8
                        signalsRemaining.remove(s)
                    }
                }
            }

            // We can solve the numbers 6, 9, 3, 2 by intersecting those segments with one or four.
            //
            signals.forEach { s ->
                val encoded = sortString(s)

                // Convert the encoded segment string to a set of chars for easy intersection tests.
                val encodedSegmentSet = encoded.toCharArray().toSet()

                when (encoded.length) {
                    6 -> {
                        // Six segment numbers can be 0, 6, or 9
                        // If the intersections of the six segment being tested and the segments in the number 1
                        // result in one intersection, the number is a 6.
                        // Six segment intersected with four segment resulting in 4 segments mean the number is a 9.

                        if (numberOneCipherSegments.intersect(encodedSegmentSet).size == 1) {
                            encodedToNumberMap[encoded] = 6
                            signalsRemaining.remove(s)
                        } else if (numberFourCipherSegments.intersect(encodedSegmentSet).size == 4) {
                            encodedToNumberMap[encoded] = 9
                            signalsRemaining.remove(s)
                        }
                    }
                    5 -> {
                        // Five segment numbers can be 2, 3, or 5.
                        // If the intersections of the five segment being tested and the segments in the number 1
                        // result in two intersections, the number is a 3.
                        // Five segments intersected with four segment resulting in a 2 means the number is a 2.
                        if (numberOneCipherSegments.intersect(encodedSegmentSet).size == 2) {
                            encodedToNumberMap[encoded] = 3
                            signalsRemaining.remove(s)
                        } else if (numberFourCipherSegments.intersect(encodedSegmentSet).size == 2) {
                            encodedToNumberMap[encoded] = 2
                            signalsRemaining.remove(s)
                        }
                    }
                }
            }

            // By now we have solved 1, 4, 7, 8 (unique segment count), 6, 9 (6 segment count) and 2, 3 (5 segment count)
            // Whatever is left, the six segment count is 0, and the five segment count is 5.
            signalsRemaining.forEach {s ->
                val sSorted = sortString(s)

                if (sSorted.length == 5) {
                    encodedToNumberMap[sSorted] = 5
                } else if (sSorted.length == 6) {
                    encodedToNumberMap[sSorted] = 0
                }
            }

            var lineSum = 0

            outputs.forEachIndexed {i, o ->
                val oSorted = sortString(o)

                val multiplier = 10.0.pow(3 - i.toDouble())

                lineSum += encodedToNumberMap[oSorted]!! * multiplier.toInt()
            }

            sum += lineSum
        }

        return sum
    }

    private fun sortString(str:String): String {
        return str.toCharArray().sorted().joinToString("")
    }
}