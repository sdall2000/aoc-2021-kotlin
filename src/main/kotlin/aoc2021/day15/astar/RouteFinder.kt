package aoc2021.day15.astar

import java.util.*

class RouteFinder<T : GraphNode>(val graph: Graph<T>, val nextNodeScorer: Scorer<T>, val targetScorer: Scorer<T>) {

    fun findRoute(from: T, to: T): List<T> {
        val allNodes = HashMap<T, RouteNode<T>>()
        val openSet = PriorityQueue<RouteNode<T>>()

        val start = RouteNode(from, null, 0.0, targetScorer.computeCost(from, to))
        allNodes[from] = start
        openSet.add(start)

        while (!openSet.isEmpty()) {
//            println("Open Set contains: " + openSet.map(RouteNode::getCurrent).collect(Collectors.toSet()));
            val next = openSet.poll()
//            log.debug("Looking at node: " + next);
            if (next.current == to) {
//                log.debug("Found our destination!");

                val route = ArrayList<T>()
                var current = next
                do {
                    route.add(0, current.current)
                    current = allNodes.get(current.previous)
                } while (current != null)

//                log.debug("Route: " + route);
                return route
            }

            graph.getConnections(next.current)!!.forEach { connection ->
                val newScore = next.routeScore + nextNodeScorer.computeCost(next.current, connection)
                val nextNode = allNodes.getOrDefault(connection, RouteNode(connection))
                allNodes[connection] = nextNode

                if (nextNode.routeScore > newScore) {
                    nextNode.previous = next.current
                    nextNode.routeScore = newScore
                    nextNode.estimatedScore = newScore + targetScorer.computeCost(connection, to)
                    openSet.add(nextNode)
//                    log.debug("Found a better route to node: " + nextNode);
                }
            }
        }

        throw IllegalStateException("No route found")
    }
}