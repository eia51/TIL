package impl

import java.util.*

data class Point(val y: Int, val x: Int, var d: Int)
data class PointDirection(val front: Point, val back: Point)

private val dirMap = HashMap<Int, PointDirection>()
private var N = 0
private var M = 0
private lateinit var start: Point
private lateinit var field: Array<IntArray>
private lateinit var isClean: Array<BooleanArray>

fun main() {
    init()
    input()
    solve()
}

private fun init() {
    dirMap[0] = PointDirection(Point(-1, 0, 0), Point(1, 0, 0))
    dirMap[1] = PointDirection(Point(0, 1, 1), Point(0, -1, 1))
    dirMap[2] = PointDirection(Point(1, 0, 2), Point(-1, 0, 2))
    dirMap[3] = PointDirection(Point(0, -1, 3), Point(0, 1, 3))
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    N = nextInt()
    M = nextInt()

    start = Point(nextInt(), nextInt(), nextInt())
    field = Array(N) { IntArray(M) { nextInt() } }
    isClean = Array(N) { BooleanArray(M) { false } }
}

// problem solve
private fun solve() {
    var clean = 0

    val dq = ArrayDeque<Point>()
    dq.add(start)

    while (dq.isNotEmpty()) {
        val curr = dq.poll()
        // 1.clean
        if (!isClean[curr.y][curr.x]) {
            isClean[curr.y][curr.x] = true
            clean++
        }

        // 2.a~b
        var goNext = false
        for (i in 1..4) {
            val dir = dirMap[getLeft(curr.d)]?.front!!
            val next = Point(curr.y + dir.y, curr.x + dir.x, getLeft(curr.d))
            if (field[next.y][next.x] == 0 && !isClean[next.y][next.x]) {
                goNext = true
                dq.add(next)
                break
            }
            curr.d = getLeft(curr.d)
        }
        if (goNext) continue

        val dir = dirMap[curr.d]?.back!!
        val next = Point(curr.y + dir.y, curr.x + dir.x, curr.d)
        // 2.c
        if (field[next.y][next.x] == 0)
            dq.add(next)
        // 2.d
        else break
    }
    println(clean)
}

private fun getLeft(dir: Int): Int {
    return (dir + 3) % 4
}
