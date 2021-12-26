package aoc2021.day24

class ArithmeticLogicUnit {

    private val INPUT = "inp"
    private val ADD = "add"
    private val MULTIPLY = "mul"
    private val DIVIDE = "div"
    private val MODULUS = "mod"
    private val EQUAL = "eql"

    private val wIndex = 0
    private val xIndex = 1
    private val yIndex = 2
    private val zIndex = 3

    fun largestModelNumber(lines: List<String>): Long {

        val paramIndexMap = mapOf('w' to 0, 'x' to 1, 'y' to 2, 'z' to 3)

        var done = false

        var z = 0L

//        val numberArray = intArrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9)
        val numberArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)

        while (!done) {
            // Loop until solution found

            val variableArray = longArrayOf(0, 0, 0, 0)

            try {
                var numberIndex = 0

                lines.forEachIndexed { lineIndex, line ->

                    val split = line.split(" ")

                    val instruction = split[0]
                    val param1 = split[1]
                    val param2 = if (split.size == 3) split[2] else " "

                    val varIndex1: Int = if (param1[0] in 'w'..'z') paramIndexMap[param1[0]]!! else -1
                    val varIndex2: Int = if (param2[0] in 'w'..'z') paramIndexMap[param2[0]]!! else -1

                    val val1 = variableArray[varIndex1]

                    when (instruction) {
                        INPUT -> {
//                            println("Input read at program line $lineIndex.  numberIndex is $numberIndex, value is ${numberArray[numberIndex]}")
                            variableArray[varIndex1] = numberArray[numberIndex].toLong()
                            numberIndex++
                        }
                        ADD -> {
                            val val2 = if (varIndex2 != -1) variableArray[varIndex2] else param2.toLong()
                            variableArray[varIndex1] = val1 + val2
                        }
                        MULTIPLY -> {
                            val val2 = if (varIndex2 != -1) variableArray[varIndex2] else param2.toLong()
                            variableArray[varIndex1] = val1 * val2
                        }
                        DIVIDE -> {
                            val val2 = if (varIndex2 != -1) variableArray[varIndex2] else param2.toLong()
                            variableArray[varIndex1] = val1 / val2
                        }
                        MODULUS -> {
                            val val2 = if (varIndex2 != -1) variableArray[varIndex2] else param2.toLong()
                            variableArray[varIndex1] = val1 % val2
                        }
                        EQUAL -> {
                            val val2 = if (varIndex2 != -1) variableArray[varIndex2] else param2.toLong()
                            variableArray[varIndex1] = if (val1 == val2) 1 else 0
                        }
                    }
                }

                z = variableArray[3]

                if (z == 0L) {
                    println("Found a solution: $numberArray")
                    done = true
                }

                if (!done) {
                    increment(numberArray)
                }
            } catch (e: Exception) {
                println("Number failed: $numberArray numberIndex: w: ${variableArray[0]}, x: ${variableArray[1]}, y:${variableArray[2]}, z:${variableArray[3]}")
                e.printStackTrace()
            }
        }

//        println("Z is $z, number is $number")

        val sb = StringBuilder()

        numberArray.forEach {
            sb.append(it.toString())
        }

        return sb.toString().toLong()
    }

    fun decrement(numberArray: IntArray) {
        var index = numberArray.size - 1

        var done = false

        while (!done && index >= 0) {
            if (numberArray[index] > 1) {
                numberArray[index]--
                done = true
            } else {
                numberArray[index] = 9
                index--
            }
        }
    }

    fun increment(numberArray: IntArray) {
        var index = numberArray.size - 1

        var done = false

        while (!done && index >= 0) {
            if (numberArray[index] < 9) {
                numberArray[index]++
                done = true
            } else {
                numberArray[index] = 1
                index--
            }
        }
    }
}