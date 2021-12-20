package aoc2021.day15

import aoc2021.day15.astar.Scorer

// This is the A* heuristic, that calculats the actual X/Y distance between the points, to influence the path to try first.
class ChiltonNextNodeScorer : Scorer<ChitonNode> {
    override fun computeCost(from: ChitonNode, to: ChitonNode): Double {
        // The cost to go to the next node is simply the next node's risk value
        return to.risk.toDouble()
    }
}