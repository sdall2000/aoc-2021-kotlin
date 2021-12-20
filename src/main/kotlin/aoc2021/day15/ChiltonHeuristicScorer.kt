package aoc2021.day15

import aoc2021.day15.astar.Scorer
import kotlin.math.pow
import kotlin.math.sqrt

// This is the A* heuristic, that calculats the actual X/Y distance between the points, to influence the path to try first.
class ChiltonHeuristicScorer : Scorer<ChitonNode> {
    override fun computeCost(from: ChitonNode, to: ChitonNode): Double {
        return sqrt((from.x - to.x).toDouble().pow(2.0) + (from.y - to.y).toDouble().pow(2.0))
    }
}