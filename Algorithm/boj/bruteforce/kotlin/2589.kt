import java.util.*
import kotlin.math.max

private data class Edge(val x: Int, val y: Int, val d: Int)

private lateinit var arr: Array<BooleanArray>
private lateinit var visit: Array<BooleanArray>
private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, 1, -1)
private val lands = ArrayList<Edge>()
private var n = 0
private var m = 0

fun main() {
    input()
    pro()
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    m = nextInt()

    visit = Array(n) { BooleanArray(m) }
    arr = Array(n) { BooleanArray(m) }

    for (i in 0 until n) {
        val line = readLine()
        for (j in 0 until m) {
            if (line[j] == 'L') {
                arr[i][j] = true
                lands.add(Edge(i, j, 0))
            }
        }
    }
}

private fun pro() {
    var maxDist = 0
    for (land in lands) {
        val dist = bfs(land)
        maxDist = max(dist, maxDist)
    }
    println(maxDist)
}

private fun bfs(land: Edge): Int {
    for (i in 0 until n)
        for (j in 0 until m)
            visit[i][j] = false

    val dq = ArrayDeque<Edge>()
    visit[land.x][land.y] = true
    dq.add(land)

    var maxDist = 0
    while (!dq.isEmpty()) {
        val item = dq.poll()
        maxDist = max(maxDist, item.d)

        for (i in 0 until 4) {
            val nx = item.x + dx[i]
            val ny = item.y + dy[i]

            if (nx in 0 until n && ny in 0 until m &&
                !visit[nx][ny] && arr[nx][ny]
            ) {
                visit[nx][ny] = true
                dq.add(Edge(nx, ny, item.d + 1))
            }
        }
    }
    return maxDist
}
