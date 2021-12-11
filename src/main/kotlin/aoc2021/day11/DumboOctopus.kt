package aoc2021.day11

class DumboOctopus {
    fun part1(octopusGrid:Array<IntArray>): Int {
        var flashes = 0

        repeat(100) {
            octopusGrid.forEachIndexed { rowIndex, ints ->
                ints.forEachIndexed { colIndex, _ ->
                    octopusGrid[rowIndex][colIndex]++
                }
            }

            // Handle flashes.
            octopusGrid.forEachIndexed { rowIndex, ints ->
                ints.forEachIndexed { colIndex, octopus ->
                    if (octopus == 10) {
                        flashes += flash(octopusGrid, rowIndex, colIndex)
                    }
                }
            }

            // Now, reset any elements >= 10 to 0
            octopusGrid.forEachIndexed { rowIndex, ints ->
                ints.forEachIndexed { colIndex, octopus ->
                    if (octopus >= 10) {
                        octopusGrid[rowIndex][colIndex] = 0
                    }
                }
            }
        }

        return flashes
    }

    fun part2(octopusGrid:Array<IntArray>): Int {
        val totalSize = octopusGrid.size * octopusGrid[0].size

        var flashes = 0

        var step = 0

        while(true) {
            step++
            octopusGrid.forEachIndexed { rowIndex, ints ->
                ints.forEachIndexed { colIndex, _ ->
                    octopusGrid[rowIndex][colIndex]++
                }
            }

            var localFlashes = 0

            // Handle flashes.
            octopusGrid.forEachIndexed { rowIndex, ints ->
                ints.forEachIndexed { colIndex, octopus ->
                    if (octopus == 10) {
                        localFlashes += flash(octopusGrid, rowIndex, colIndex)
                    }
                }
            }

            if (localFlashes == totalSize) {
                return step
            }

            flashes += localFlashes

            // Now, reset any elements >= 10 to 0
            octopusGrid.forEachIndexed { rowIndex, ints ->
                ints.forEachIndexed { colIndex, octopus ->
                    if (octopus >= 10) {
                        octopusGrid[rowIndex][colIndex] = 0
                    }
                }
            }
        }
    }

    private fun flash(octopusGrid:Array<IntArray>, row:Int, col:Int):Int {
        // Start at one for the current row/col.
        var flashes = 1
        // Increment current cell to 11, rather than immediately setting it to zero.
        // That will prevent it from re-flashing during this turn.
        octopusGrid[row][col] = 11
        for (rowOffset in -1..1) {
            val newRow = row + rowOffset
            for (colOffset in -1..1) {
                val newCol = col + colOffset
                if (inBounds(octopusGrid, newRow, newCol)) {
                    val value = octopusGrid[newRow][newCol]

                    // Only process values <= 9.  Let values that are already 10 flash when it is their turn
                    // during the original scan.  We probably could flash those here as well also.
                    if (value <= 9) {
                        octopusGrid[newRow][newCol] = value + 1
                        if (value == 9) {
                            flashes += flash(octopusGrid, newRow, newCol)
                        }
                    }
                }
            }
        }

        return flashes
    }

    private fun inBounds(octopusGrid:Array<IntArray>, row:Int, col:Int): Boolean {
        return row in octopusGrid.indices && col in octopusGrid[0].indices
    }
}