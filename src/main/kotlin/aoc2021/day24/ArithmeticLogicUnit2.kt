package aoc2021.day24

class ArithmeticLogicUnit2 {

    // Observed deltas for processing the 14 different inputs.
    private val add1 = longArrayOf(12, 11, 10, 10, -16, 14, 12, -4, 15, -7, -8, -4, -15, -8)
    private val div = longArrayOf(1, 1, 1, 1, 26, 1, 1, 26, 1, 26, 26, 26, 26, 26)
    private val add2 = longArrayOf(6, 12, 5, 10, 7, 0, 4, 12, 14, 13, 10, 11, 9, 9)

    fun largestModelNumber(): Long {
        return recurseMax(0, 0, "")!!
    }

    fun smallestModelNumber(): Long {
        return recurseMin(0, 0, "")
    }

    fun recurseMax(z: Long, index: Int, answer:String = ""): Long? {
        if (index > 13) {
            return 0
        }

        for (w in 9L downTo 1) {
            val zNew = subRoutine(w, z, div[index], add1[index], add2[index])

            if (zNew == 0L && index == 13) {
                val solution = answer + w
                println("Found answer: $solution")
                return solution.toLong()
            } else {
                val solution = recurseMax(zNew, index + 1, answer + w)
                if (solution != null) {
                    return solution
                }
            }
        }

        return null
    }

    fun recurseMin(z: Long, index: Int, answer:String = ""): Long {

        if (index > 13) {
//            println("Index is > 13 ($index).  Failed to find result.")
            return 0
        }

        for (w in 1L..9) {
            val zNew = subRoutine(w, z, div[index], add1[index], add2[index])

            if (zNew == 0L && index == 13) {
                println("Found answer: ${answer}$w")
                System.exit(0)
            } else {
                recurseMin(zNew, index + 1, answer + w)
            }
        }

        return 0
    }

    fun subRoutine(w: Long, zOutput: Long, div: Long, add1: Long, add2: Long): Long {
        var z = zOutput

        // Add 1 values are 12, 11, 10, 10, -16, 14, 12, -4, 15, -7, -8, -4, -15, -8
        // First time through, z is 0, x is add1
        var x = z % 26 + add1

        // x = !(x == w)
        x = if (x != w) 1 else 0

        // Y will be 1 or 26 after this.
        var y = 25 * x + 1

        // Div values are 1, 1, 1, 1, 26, 1, 1, 26, 1, 26, 26, 26, 26, 26
        z = z / div * y

        // y will be zero or w + add2 here.
        // Add 2 values are 6, 12, 5, 10, 7, 0, 4, 12, 14, 13, 10, 11, 9, 9
        y = x * (w + add2)

        z += y

        return z
    }
}