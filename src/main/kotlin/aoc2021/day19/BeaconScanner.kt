package aoc2021.day19

class BeaconScanner {
    fun getBeaconCount(lines:List<String>):Int {

        // --- scanner 0 ---
        // 785,-772,752
        //
        // --- scanner 1 ---

        val pointStrings = mutableListOf<String>()
        var scannerId:Int = 0

        val scanners = mutableListOf<Scanner>()

        lines.forEach { line ->
            if (line.isEmpty() && pointStrings.isNotEmpty()) {
                val points = pointStrings.map {
                    val (xStr, yStr, zStr) = it.split(",")

                    Point(xStr.toInt(), yStr.toInt(), zStr.toInt())
                }

                scanners.add(Scanner(scannerId, points))

                pointStrings.clear()
                scannerId = 0
            } else if (line.startsWith("---")) {
                scannerId = line.substringAfter("--- scanner ").substringBefore(" ---").toInt()
            } else {
                pointStrings.add(line)
            }
        }

        // In case last set of data was not persisted due to no final blank line.
        if (pointStrings.isNotEmpty()) {
            val points = pointStrings.map {
                val (xStr, yStr, zStr) = it.split(",")

                Point(xStr.toInt(), yStr.toInt(), zStr.toInt())
            }

            scanners.add(Scanner(scannerId, points))

            pointStrings.clear()
            scannerId = 0
        }

        return 0
    }
}

data class Scanner(val scannerId: Int, val points:List<Point>) {
}

data class Point(val x:Int, val y:Int, val z:Int)