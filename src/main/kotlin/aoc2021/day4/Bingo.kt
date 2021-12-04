package aoc2021.day4

class Bingo {
    fun playBingo(lines: List<String>): Int {
        // First line are the numbers to be picked.

        val numbers = lines[0].split(",").map { it.toInt() }

        val boards = buildBoards(lines)

        numbers.forEach { number ->
            boards.forEach { b ->
                if (b.playNumber(number)) {
                    return number * b.calculateBoardScore()
                }
            }
        }

        return 0
    }

    fun playBingoLastWinner(lines: List<String>): Int {
        // First line are the numbers to be picked.

        val numbers = lines[0].split(",").map { it.toInt() }

        val boards = buildBoards(lines)

        // Get the bingo numbers
        numbers.forEach { number ->
            // Play the bingo number on each board
            boards.forEach { b ->
                if (b.playNumber(number)) {
                    // See if this is the last board
                    if (boards.size == 1) {
                        return number * boards[0].calculateBoardScore()
                    }
                }
            }

            // Remove winning boards from the collection.
            boards.removeIf { it.isWinner() }
        }

        return 0
    }

    private fun buildBoards(lines: List<String>): MutableList<Board> {
        // Now build up the boards
        val boardRows = mutableListOf<String>()
        val boards = mutableListOf<Board>()

        for (i in 1 until lines.size) {
            if (lines[i].trim().isNotEmpty()) {
                boardRows.add(lines[i])
                if (boardRows.size == 5) {
                    // We have a full board.
                    boards.add(Board(boardRows))
                    boardRows.clear()
                }
            }
        }

        return boards
    }

    class Board(boardRows: List<String>) {
        private var boardGrid = mutableListOf<List<Int>>()
        private var boardMap = mutableMapOf<Int, Coordinate>()
        private var rowCounts:IntArray
        private var colCounts:IntArray
        private var winner = false

        init {
            // Track how many numbers are left in a given row/column.
            // Once a value goes to zero, the bingo card is solved.
            rowCounts = IntArray(boardRows.size) { boardRows.size}
            colCounts = IntArray(boardRows.size) { boardRows.size}

            boardRows.forEachIndexed { indexRow, line ->
                val numbers = line.trim().split("\\s+".toRegex()).map { n -> n.trim().toInt() }.toMutableList()
                boardGrid.add(numbers)

                numbers.forEachIndexed { indexCol, number ->
                    boardMap[number] = Coordinate(indexRow, indexCol)
                }
            }
        }

        fun playNumber(number: Int): Boolean {
            val coordinate = boardMap.remove(number)

            if (coordinate != null) {
                rowCounts[coordinate.row]--
                if (rowCounts[coordinate.row] == 0) {
                    winner = true
                    return true
                }

                colCounts[coordinate.col]--
                if (colCounts[coordinate.col] == 0) {
                    winner = true
                    return true
                }
            }

            return false
        }

        fun calculateBoardScore(): Int {
            return boardMap.keys.sum()
        }

        fun isWinner(): Boolean {
            return winner
        }
    }

    data class Coordinate(var row: Int, var col: Int)
}