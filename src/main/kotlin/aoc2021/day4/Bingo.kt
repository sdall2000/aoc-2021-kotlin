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
            if (lines[i].isNotEmpty()) {
                boardRows.add(lines[i].trim())
            } else if (boardRows.size > 0) {
                // We are on an empty line and we have board rows.  Create a board.
                boards.add(Board(boardRows))
                boardRows.clear()
            }
        }

        // If there are still boardRows in the collection, then we need to build our final board.
        boards.add(Board(boardRows))

        return boards
    }

    class Board(boardRows: List<String>) {
        private var boardMap = mutableMapOf<Int, Coordinate>()
        private var rowCounts: IntArray
        private var colCounts: IntArray
        private var winner = false

        init {
            // Track how many numbers are left in a given row/column.
            // Once a value goes to zero, the bingo card is solved.
            val boardRowSize = boardRows.size
            val boardColumnSize = boardRows[0].split("\\s+".toRegex()).size

            // So in a 5x5 board, this creates an array of 5 entries, all initialized to 5.
            // As bingo numbers are played, the corresponding row/column will be decremented.
            // When zero is reached in either a row or a column, the board is solved.
            rowCounts = IntArray(boardRowSize) { boardRowSize }
            colCounts = IntArray(boardColumnSize) { boardColumnSize }

            boardRows.forEachIndexed { indexRow, line ->
                val numbers = line.trim().split("\\s+".toRegex()).map { n -> n.trim().toInt() }.toMutableList()

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