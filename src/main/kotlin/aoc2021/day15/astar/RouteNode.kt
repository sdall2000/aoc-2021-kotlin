package aoc2021.day15.astar

import java.util.*

class RouteNode<T : GraphNode>(
    val current: T,
    var previous: T? = null,
    var routeScore: Double = Double.POSITIVE_INFINITY,
    var estimatedScore: Double = Double.POSITIVE_INFINITY
) : Comparable<RouteNode<T>> {

    override fun compareTo(other: RouteNode<T>): Int {
        return estimatedScore.compareTo(other.estimatedScore)
    }

    override fun toString(): String {
        return StringJoiner(", ", RouteNode::class.simpleName + "[", "]").add("current=$current")
            .add("previous=$previous").add("routeScore=$routeScore").add("estimatedScore=$estimatedScore")
            .toString()
    }
}