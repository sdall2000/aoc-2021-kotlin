package aoc2021.day13

class TransparentOrigami {
    fun getVisibleDotsAfterFirstFold(instructions:List<String>): Int {

        val points = HashSet<Point>()
        val folds = ArrayList<Fold>()

        instructions.forEach {
            if (it.isNotEmpty()) {
                if (it.startsWith("fold")) {
                    val split = it.split(" ")[2]
                    val (axisStr, positionStr) = split.split("=")
                    folds.add(Fold(axisStr[0], positionStr.toInt()))
                } else {
                    val (xStr, yStr) = it.split(",")
                    points.add(Point(xStr.toInt(), yStr.toInt()))
                }
            }
        }

        return fold(points, folds[0]).size
    }

    fun getVisibleDotsAfterAllFolds(instructions:List<String>): Int {

        var points = HashSet<Point>()
        val folds = ArrayList<Fold>()

        instructions.forEach {
            if (it.isNotEmpty()) {
                if (it.startsWith("fold")) {
                    val split = it.split(" ")[2]
                    val (axisStr, positionStr) = split.split("=")
                    folds.add(Fold(axisStr[0], positionStr.toInt()))
                } else {
                    val (xStr, yStr) = it.split(",")
                    points.add(Point(xStr.toInt(), yStr.toInt()))
                }
            }
        }

        folds.forEach {
            points = fold(points, it)
        }

        print(points)

        return points.size
    }

    private fun fold(points:HashSet<Point>, fold:Fold):HashSet<Point> {
        val newPoints = HashSet<Point>()

        if (fold.axis == 'x') {
            // Get any point with an x value less than the fold point.
            newPoints.addAll(points.filter {
                it.x < fold.position
            })

            // Get any point with an x value greater than the fold point.
            // These will fold into the existing points.
            val foldingPoints = points.filter {
                it.x > fold.position
            }

            foldingPoints.forEach {
                newPoints.add(Point(fold.position - (it.x - fold.position), it.y))
            }

        } else {
            // Get any point with a y value less than the fold point.
            newPoints.addAll(points.filter {
                it.y < fold.position
            })

            // Get any point with a y value greater than the fold point.
            // These will fold into the existing points.
            val foldingPoints = points.filter {
                it.y > fold.position
            }

            foldingPoints.forEach {
                newPoints.add(Point(it.x, fold.position - (it.y - fold.position)))
            }
        }

        return newPoints
    }

    private fun print(points:Collection<Point>) {
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val point = Point(x, y)
                if (points.contains(point)) {
                    print("█")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }
}

data class Point(val x:Int, val y:Int)

data class Fold(val axis:Char, val position:Int)