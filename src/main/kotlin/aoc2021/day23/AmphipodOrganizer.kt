package aoc2021.day23

import java.util.concurrent.atomic.AtomicInteger

class AmphipodOrganizer {
    // Actual puzzle input

    // Part 1:
    //    #############
    //    #...........#
    //    ###D#C#B#C###
    //      #D#A#A#B#
    //      #########

    // Part 2: (per the problem, middle two rows are inserted)
    //    #############
    //    #...........#
    //    ###D#C#B#C###
    //      #D#C#B#A#
    //      #D#B#A#C#
    //      #D#A#A#B#
    //      #########

    fun part1(lines: List<String>): Int {
        val positions = Array(lines.size) { i -> charArrayOf('a') }

        lines.forEachIndexed { lineIndex, line ->
            positions[lineIndex] = line.toCharArray()
        }

        // Semi-dynamically derive the home y position range.
        val homeYupper = 2
        val homeYlower = lines.size - 2

        val maze = Maze(positions, homeYupper, homeYlower)

        return maze.solve(0, AtomicInteger(Int.MAX_VALUE))!!
    }
}

class Maze(val positions: Array<CharArray>, val homeYupper:Int, val homeYlower: Int) {
    val amphipods = mutableListOf<Amphipod>()

    init {
        positions.forEachIndexed { rowIndex, charArray ->
            charArray.forEachIndexed { colIndex, char ->
                when (char) {
                    'A' -> amphipods.add(Amphipod("Amber", 1, colIndex, rowIndex, 3, homeYupper, homeYlower))
                    'B' -> amphipods.add(Amphipod("Bronze", 10, colIndex, rowIndex, 5, homeYupper, homeYlower))
                    'C' -> amphipods.add(Amphipod("Copper", 100, colIndex, rowIndex, 7, homeYupper, homeYlower))
                    'D' -> amphipods.add(Amphipod("Desert", 1000, colIndex, rowIndex, 9, homeYupper, homeYlower))
                }
            }
        }
    }

    fun solve(scoreSoFar: Int, lowestScore: AtomicInteger): Int? {
        if (scoreSoFar >= lowestScore.get()) {
            return null
        }

        val homeCount = amphipods.count {
            it.home(positions)
        }

        if (homeCount == amphipods.size) {
            lowestScore.set(scoreSoFar)
            return scoreSoFar
        }

        // We aren't solved yet, see if there are legal moves.
        var bestScore: Int? = null

        amphipods.forEach { amphipod ->
            amphipod.legalMoves(positions).forEach { legalMove ->
                val newPositions = clonePositions()

                val y = legalMove.first.second
                val x = legalMove.first.first
                val score = legalMove.second

                newPositions[amphipod.y][amphipod.x] = '.'
                newPositions[y][x] = amphipod.name[0]

                val maze = Maze(newPositions, homeYupper, homeYlower)

                val solveScore = maze.solve(scoreSoFar + score, lowestScore)

                if (solveScore != null) {
                    if (bestScore == null) {
                        bestScore = solveScore
                    } else {
                        bestScore = bestScore!!.coerceAtMost(solveScore)
                    }
                }
            }
        }

        return bestScore
    }

    private fun clonePositions(): Array<CharArray> {
        val newPositions = Array(positions.size) { _ -> charArrayOf('a') }

        positions.forEachIndexed { rowIndex, charArray ->
            newPositions[rowIndex] = charArray.copyOf()
        }

        return newPositions
    }
}

data class Amphipod(val name: String, val moveCost: Int, var x: Int, var y: Int, val homeX: Int, val homeYupper: Int, val homeYlower: Int) {

    private val inHallAboveRoom = intArrayOf(3, 5, 7, 9)

    fun legalMoves(maze: Array<CharArray>): List<Pair<Pair<Int, Int>, Int>> {
        val moves = mutableListOf<Pair<Pair<Int, Int>, Int>>()

        if (home(maze)) {
            // No legal moves if we are already home.
            return moves
        }

        if (inHall()) {
            if (homeIsOpen(maze)) {
                // If we are in the hall, there are zero or one legal moves.
                val direction: Int

                if (x > homeX) {
                    direction = -1
                } else {
                    direction = 1
                }

                var curX = x

                curX += direction
                var moveCount = 1

                while (curX != homeX && maze[y][curX] == '.') {
                    curX += direction
                    moveCount++
                }

                // Make sure we reached the x position above our home.
                if (curX == homeX) {
                    // We are over the home room.
                    // Move to the lowest available position.

                    var curY = y + 1
                    moveCount++

                    while (maze[curY+1][curX] == '.') {
                        moveCount++
                        curY++
                    }

                    moves.add(Pair(Pair(curX, curY), moveCount * moveCost))
                }
            }
        } else {
            // Not in hall, in a side room.
            // Note the side room may be my home room, but in that case I am in the homeYupper position and
            // a different Amphipod is in the lower home position so I still have to move.
            var moveCount = 0

            // If successful, curY is in the hall.
            val curY = homeYupper - 1

            // Calculate how many moves to get out of the room, if it's even possible.

            var clear = true

            for (chkY in (y - 1) downTo homeYupper) {
                if (maze[chkY][x] != '.') {
                    clear = false
                }
            }

            if (clear) {
                // How many moves to get into the hall
                moveCount += (y - homeYupper + 1)
            } else {
                // Can't get out
                return moves
            }

            var curX = x

            val startX = curX
            val startMoveCount = moveCount

            // Move right until we can no longer, and add positions/scores
            curX++
            moveCount++

            while (maze[curY][curX] == '.') {
                if (!inHallAboveRoom.contains(curX)) {
                    moves.add(Pair(Pair(curX, curY), moveCount * moveCost))
                }
                curX++
                moveCount++
            }

            moveCount = startMoveCount
            curX = startX

            // Move left until we can no longer, and add positions/scores
            curX--
            moveCount++

            while (maze[curY][curX] == '.') {
                if (!inHallAboveRoom.contains(curX)) {
                    moves.add(Pair(Pair(curX, curY), moveCount * moveCost))
                }
                curX--
                moveCount++
            }
        }

        return moves
    }

    fun inHall(): Boolean {
        // Hardcode for now.
        return y == 1
    }

    fun homeIsOpen(maze: Array<CharArray>): Boolean {
        var freeSpace = false

        for (chkY in homeYupper..homeYlower) {
            val chr = maze[chkY][homeX]
            if (chr == '.') {
                // At least one spot available
                freeSpace = true
            } else if (chr != name[0]) {
                // There should be no other characters.
                return false
            }
        }

        return freeSpace
    }

    fun home(maze: Array<CharArray>): Boolean {
        // First ensure we are on the x home
        if (x == homeX) {
            // now check cells below us for foreign amphipods
            for (chkY in y + 1..homeYlower) {
                val chr = maze[chkY][x]
                if (chr != name[0]) {
                    return false
                }
            }

            return true
        }

        return false
    }
}