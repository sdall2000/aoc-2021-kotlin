package aoc2021.day21

class DiracDice {
    fun part1(player1StartPosition:Int, player2StartPosition: Int): Int {
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

    fun part2(player1StartPosition:Int, player2StartPosition: Int): Long {
        return 0
    }
}

class DeterministicDie(val sides:Int = 100) {
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

class GameUniverse