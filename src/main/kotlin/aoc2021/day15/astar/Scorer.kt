package aoc2021.day15.astar

interface Scorer<T : GraphNode> {
    fun computeCost(from: T, to: T): Double
}