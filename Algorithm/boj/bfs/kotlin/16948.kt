import java.util.*

private data class Edge(val y: Int, val x: Int, val d: Int)

private val dy = intArrayOf(-2, -2, 0, 0, 2, 2)
private val dx = intArrayOf(-1, 1, -2, 2, -1, 1)

private lateinit var start: Edge
private lateinit var dest: Edge
private lateinit var visit: Array<BooleanArray>
private var n = 0


fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    start = Edge(nextInt(), nextInt(), 0)
    dest = Edge(nextInt(), nextInt(), 0)
    visit = Array(n) { BooleanArray(n) }
}

private fun solve() {
    val dq = ArrayDeque<Edge>()
    dq.add(start)
    visit[start.y][start.x] = true

    while (dq.isNotEmpty()) {
        val curr = dq.poll()
        if (curr.x == dest.x && curr.y == dest.y) {
            println(curr.d)
            return
        }

        for (i in dy.indices) {
            val ny = curr.y + dy[i]
            val nx = curr.x + dx[i]

            if (ny !in 0 until n || nx !in 0 until n || visit[ny][nx])
                continue

            dq.add(Edge(ny, nx, curr.d + 1))
            visit[ny][nx] = true
        }
    }

    println(-1)
}
