package aoc2021.day9

class LavaTubes {
    fun sumLowPointRiskLevels(lines: List<String>): Int {
        val lowPoints = getLowPoints(lines)

        return lowPoints.sumOf { p -> p.value + 1 }
    }

    fun multiplyThreeLargestBasins(lines: List<String>): Int {
        val lowPoints = getLowPoints(lines)

        val map = HashMap<Point, Int>()

        lowPoints.forEach { point ->
            val basinSize = calculateBasinSize(lines, point)

            map[point] = basinSize
        }

        var mult = 1

        map.values.sortedDescending().stream().limit(3).forEach {
            mult *= it
        }

        return mult
    }

    private fun calculateBasinSize(lines: List<String>, point: Point): Int {
        // The number 9 is not included as part of the basin.
        // Only look horizontally and vertically - not diagonally.
        val set = HashSet<Point>()

        calculateBasinSet(lines, point, set)

        return set.size
    }

    private fun calculateBasinSet(lines: List<String>, point: Point?, set: MutableSet<Point>) {
        // Terminal case.  The point is null, the set already contains the point, or the point value is 9.
        if (point == null || set.contains(point) || point.value == 9) {
            return
        }

        set.add(point)

        // Recursively check neighbors
        calculateBasinSet(lines, getPoint(lines, point.row-1, point.col), set)
        calculateBasinSet(lines, getPoint(lines, point.row, point.col+1), set)
        calculateBasinSet(lines, getPoint(lines, point.row+1, point.col), set)
        calculateBasinSet(lines, getPoint(lines, point.row, point.col-1), set)
    }

    private fun getPoint(lines:List<String>, row:Int, col:Int):Point? {
        var point:Point? = null

        if (isPositionValid(lines, row, col)) {
            point = Point(row, col, lines[row][col].toString().toInt())
        }

        return point
    }

    private fun getLowPoints(lines: List<String>): List<Point> {
        val lowPointList = ArrayList<Point>()

        lines.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, strValue ->
                if (isLowPoint(lines, rowIndex, colIndex)) {
                    val value = strValue.toString().toInt()

                    lowPointList.add(Point(rowIndex, colIndex, value))
                }
            }
        }

        return lowPointList
    }

    private fun isLowPoint(lines: List<String>, rowIndex: Int, colIndex: Int): Boolean {
        val elevation = getElevation(lines, rowIndex, colIndex)

        // Inspect all of the cells around this one.
        for (rowOffset in -1..1) {
            for (colOffset in -1..1) {
                if (rowOffset != 0 || colOffset != 0) {
                    val row = rowIndex + rowOffset
                    val col = colIndex + colOffset

                    if (isPositionValid(lines, row, col)) {
                        val compareElevation = getElevation(lines, row, col)

                        if (compareElevation < elevation) {
                            return false
                        }
                    }
                }
            }
        }

        return true
    }

    private fun getElevation(lines: List<String>, row: Int, col: Int): Int {
        // TODO why can't we directly convert a char to an int?
        return lines[row][col].toString().toInt()
    }

    private fun isPositionValid(lines: List<String>, row: Int, col: Int): Boolean {
        return row in lines.indices && col in lines[0].indices
    }

    data class Point(val row: Int, val col: Int, val value: Int)
}