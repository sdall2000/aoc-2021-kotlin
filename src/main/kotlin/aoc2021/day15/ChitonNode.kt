package aoc2021.day15

import aoc2021.day15.astar.GraphNode

class ChitonNode(val key: String, val x: Int, val y: Int, val risk: Int) : GraphNode {
    override fun getId(): String {
        return key
    }
}