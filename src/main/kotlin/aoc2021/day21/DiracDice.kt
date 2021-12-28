package aoc2021.day21

class DiracDice {
    fun part1(player1StartPosition: Int, player2StartPosition: Int): Int {
        val boardSpaces = 10

        var player1Score = 0
        var player2Score = 0

        var player1Turn = true

        // Internally the board spaces are 0-9.
        var player1Position = player1StartPosition - 1
        var player2Position = player2StartPosition - 1

        val deterministicDie = DeterministicDie()

        while (player1Score < 1000 && player2Score < 1000) {
            val roll = deterministicDie.roll(3)

            if (player1Turn) {
                player1Position = (player1Position + roll) % boardSpaces
                player1Score += player1Position + 1
            } else {
                player2Position = (player2Position + roll) % boardSpaces
                player2Score += player2Position + 1
            }

            player1Turn = !player1Turn
        }

        return player2Score.coerceAtMost(player1Score) * deterministicDie.dieRolls
    }

    // Key insight, from https://github.com/jonathanpaulson/AdventOfCode.
    // Use memoization.  There are only so many game states - current player position and score, other player position and score.

    fun part2(player1StartPosition: Int, player2StartPosition: Int): Long {
        val memo = HashMap<GameState, Pair<Long, Long>>()

        // Internally the board spaces are 0-9.
        val player1Position = player1StartPosition - 1
        val player2Position = player2StartPosition - 1

        val (p1Total, p2Total) = countWin(player1Position, 0, player2Position, 0, memo)

        return p1Total.coerceAtLeast(p2Total)
    }

    // This was mostly converted verbatim from Jonathan Paulson's python solution.  Variable names more explicit.
    // The important thing to realize here, is the parameters represent the current player's turn,
    // which could either be player 1 or player 2.  The Pair that is returned gives the current player's
    // score and the other player's score.
    // Note in the recursive call (and processing of the results) everything is inverted to let the other player have
    // their turn.
    private fun countWin(
        currentPlayerPosition: Int,
        currentPlayerScore: Long,
        otherPlayerPosition: Int,
        otherPlayerScore: Long,
        memo: MutableMap<GameState, Pair<Long, Long>>
    ): Pair<Long, Long> {
        // See if the other player won before proceeding.
        if (otherPlayerScore >= 21) return Pair(0L, 1L)

        val gameState = GameState(currentPlayerPosition, currentPlayerScore, otherPlayerPosition, otherPlayerScore)

        // This is the optimization key.  If game state is in the memo, just return it.
        // How many game states are there before winning?  There are 10 possible player positions,
        // 21 possible scores before winning (0-20).  So for two players,  10 * 10 * 21 * 21 = 44,100
        if (memo.contains(gameState)) return memo[gameState]!!

        var answer = Pair(0L, 0L)

        // Perform the three dice rolls, which generates 27 different outcomes (universes)
        for (die1 in 1..3) {
            for (die2 in 1..3) {
                for (die3 in 1..3) {
                    val dieTotal = die1 + die2 + die3

                    val newCurrentPlayerPosition = (currentPlayerPosition + dieTotal) % 10
                    // Remember to add 1 for the score, to convert from zero based positions (0-9) to scores (1-10)
                    val newCurrentPlayerScore = currentPlayerScore + newCurrentPlayerPosition + 1

                    // Now, it's the other player's turn.  Invert the order of other/current.
                    val (otherPlayerWins, currentPlayerWins) = countWin(otherPlayerPosition, otherPlayerScore, newCurrentPlayerPosition, newCurrentPlayerScore, memo)

                    // Which means we also invert the results.
                    answer = Pair(answer.first + currentPlayerWins, answer.second + otherPlayerWins)
                }
            }
        }

        // Save these results to the memo.
        memo[gameState] = answer

        return answer
    }
}

class DeterministicDie(val sides: Int = 100) {
    private var nextDieValue = 1
    var dieRolls = 0

    fun roll(): Int {
        val retVal = nextDieValue
        nextDieValue++

        if (nextDieValue > sides) {
            nextDieValue = 1
        }

        dieRolls++

        return retVal
    }

    fun roll(rollCount: Int): Int {
        var sum = 0

        repeat(rollCount) {
            sum += roll()
        }

        return sum
    }
}

// Represents the current players position and score, and the other player's position and score.
data class GameState(val currentPosition: Int, val currentScore: Long, val otherPosition: Int, val otherScore: Long)
