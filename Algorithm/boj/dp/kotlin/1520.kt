import java.util.*

private lateinit var visit: Array<BooleanArray>
private lateinit var arr: Array<IntArray>
private lateinit var dp: Array<IntArray>
private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, 1, -1)
private var m = 0
private var n = 0

fun main() {
    input()
    println(dfs(0, 0))
}

private fun dfs(y: Int, x: Int): Int {
    if (visit[y][x])
        return dp[y][x]

    if (y == m-1 && x == n-1)
        return 1

    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if (nx in 0 until n && ny in 0 until m && arr[y][x] > arr[ny][nx]) {
            dp[y][x] += dfs(ny, nx)
        }
    }
    visit[y][x] = true
    return dp[y][x]
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    m = nextInt()
    n = nextInt()

    arr = Array(m) { IntArray(n) { nextInt() } }
    visit = Array(m) { BooleanArray(n) }
    dp = Array(m) { IntArray(n) }
}
