import java.util.*

data class Trash(val y: Int, val x: Int)

private var N = 0
private var M = 0
private var K = 0
private var size = 0

private val dy = intArrayOf(0, 0, 1, -1)
private val dx = intArrayOf(1, -1, 0, 0)

private lateinit var arr: Array<IntArray>
private lateinit var visit: Array<BooleanArray>
private val trash = ArrayList<Trash>()

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(kotlin.io.readLine(), " ")
        return st.nextToken().toInt()
    }

    N = nextInt()
    M = nextInt()
    K = nextInt()

    arr = Array(N) { IntArray(M) { 0 } }
    visit = Array(N) { BooleanArray(M) { false } }

    repeat(K) {
        val r = nextInt() - 1
        val c = nextInt() - 1
        arr[r][c] = 1
        trash.add(Trash(r, c))
    }
}

private fun solve() {
    var maxSize = Int.MIN_VALUE
    for (item in trash) {
        if (!visit[item.y][item.x]) {
            size = 0
            dfs(item.y, item.x)
            maxSize = kotlin.math.max(size, maxSize)
        }
    }
    println(maxSize)
}

private fun dfs(y: Int, x: Int) {
    visit[y][x] = true
    size++

    for (i in 0 until 4) {
        val ny = y + dy[i]
        val nx = x + dx[i]

        if (ny in 0 until N && nx in 0 until M && !visit[ny][nx] && arr[ny][nx] == 1)
            dfs(ny, nx)
    }
}
