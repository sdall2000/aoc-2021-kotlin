package aoc2021.day3

class BinaryDiagnostic {
    fun calculatePowerConsumption(diagnosticReport: List<String>): Int {
        // This assumes all lines are the same length.
        val lineLength = diagnosticReport[0].length

        var gamma = ""
        var epsilon = ""

        for (pos in 0 until lineLength) {
            val onesCount = diagnosticReport.count { it[pos] == '1' }
            val zeroesCount = diagnosticReport.size - onesCount

            if (onesCount >= zeroesCount) {
                gamma += '1'
                epsilon += '0'
            } else {
                gamma += '0'
                epsilon += '1'
            }
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun calculateLifeSupportRating(diagnosticReport: List<String>): Int {

        val oxygen = getOxy(diagnosticReport, 0)
        val co2 = getCo2(diagnosticReport, 0)

        return oxygen * co2
    }

    private fun getOxy(candidates: List<String>, index: Int): Int {
        // Recursion termination
        if (candidates.size == 1) {
            return candidates[0].toInt(2)
        }

        val onesCount = candidates.count { it[index] == '1' }
        val zeroesCount = candidates.size - onesCount

        var targetChar = '0'

        if (onesCount >= zeroesCount) {
            targetChar = '1'
        }

        return getOxy(candidates.filter { it[index] == targetChar }, index + 1)
    }

    private fun getCo2(candidates: List<String>, index: Int): Int {
        // Recursion termination
        if (candidates.size == 1) {
            return candidates[0].toInt(2)
        }

        val onesCount = candidates.count { it[index] == '1' }
        val zeroesCount = candidates.size - onesCount

        var targetChar = '1'

        if (onesCount >= zeroesCount) {
            targetChar = '0'
        }

        return getCo2(candidates.filter { it[index] == targetChar }, index + 1)
    }
}