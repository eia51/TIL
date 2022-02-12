import java.io.*
import java.math.*
import java.security.*
import java.text.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.regex.*
import java.util.stream.*
import kotlin.collections.*
import kotlin.comparisons.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.jvm.functions.*
import kotlin.jvm.internal.*
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*



/*
 * Complete the 'solution' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. STRING s
 *  2. STRING keypad
 */

private data class Edge(val x: Int, val y: Int, val d: Int)
private val dx = intArrayOf(0, 0, 1, -1, -1, -1, 1, 1)
private val dy = intArrayOf(1, -1, 0, 0, -1, 1, 1, -1)

private fun bfs(arr: Array<CharArray>, startEdge: Edge, targetNum: Char): Edge {
    val dq = ArrayDeque<Edge>()
    val visit = Array(3) { BooleanArray(3) }

    visit[startEdge.x][startEdge.y] = true
    dq.add(startEdge)

    while(!dq.isEmpty()) {
        val item = dq.poll()
        if (arr[item.x][item.y] == targetNum)
            return item

        for (i in 0 until 8) {
            val nx = item.x + dx[i]
            val ny = item.y + dy[i]

            if (nx in 0..2 && ny in 0..2 && !visit[nx][ny]) {
                visit[nx][ny] = true
                dq.add(Edge(nx, ny, item.d + 1))
            }
        }
    }
    throw Exception("Can't find symbol")
}

fun solution(s: String, keypad: String): Int {
    // make array
    val arr = Array(3) { CharArray(3)}
    for (i in 0 until 3) {
        for (j in 0 until 3) {
            arr[i][j] = keypad[i*3 + j]
        }
    }

    //find start pos
    val idx = keypad.indexOf(s[0])
    var edge = Edge(idx / 3, idx % 3, 0)

    //calc dist using bfs
    for (i in 1 until s.length) {
        edge = bfs(arr, edge, s[i])
    }

    return edge.d
}

fun main(args: Array<String>) {
    val s = readLine()!!

    val keypad = readLine()!!

    val result = solution(s, keypad)

    println(result)
}
