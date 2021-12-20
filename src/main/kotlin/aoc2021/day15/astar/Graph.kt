package aoc2021.day15.astar

import java.util.stream.Collectors

class Graph<T : GraphNode>(val nodes: Set<T>, val connections: Map<String, Set<String>>) {

    fun getNode(id: String?): T {
        return nodes.stream()
            .filter { node: T -> node.getId() == id }
            .findFirst()
            .orElseThrow {
                IllegalArgumentException(
                    "No node found with ID"
                )
            }
    }

    fun getConnections(node: T): Set<T>? {
        return connections[node.getId()]!!.stream()
            .map { id: String? -> getNode(id) }
            .collect(Collectors.toSet())
    }
}