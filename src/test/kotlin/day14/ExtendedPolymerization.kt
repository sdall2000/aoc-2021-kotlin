package day14

class ExtendedPolymerization {
    fun part1(lines:List<String>, steps:Int): Long {
        val template = lines[0].trim()

        val pairInsertionRules = HashMap<String, String>()

        lines.forEachIndexed { index, line ->
            if (index >= 2) {
                val (pair, ch) = line.split(" -> ")

                pairInsertionRules[pair] = ch
            }
        }

        var sb = StringBuilder(template)

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
}