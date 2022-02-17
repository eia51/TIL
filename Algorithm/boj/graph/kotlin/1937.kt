import java.util.*
import kotlin.math.max

private lateinit var arr: Array<IntArray>
private lateinit var dp: Array<IntArray>
private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, -1, 1)
private var maxValue = Int.MIN_VALUE
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
    arr = Array(n) { IntArray(n) { nextInt() } }
    dp = Array(n) { IntArray(n) }
}

private fun solve() {
    for (i in 0 until n) {
        for (j in 0 until n)
            if (dp[i][j] == 0)
                maxValue = max(maxValue, dfs(i, j))
    }
    println(maxValue)
}

private fun dfs(y: Int, x: Int): Int {
    if (dp[y][x] != 0)
        return dp[y][x]

    dp[y][x] = 1
    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]
        if (nx in 0 until n && ny in 0 until n && arr[ny][nx] > arr[y][x])
            dp[y][x] = max(dfs(ny, nx) + 1, dp[y][x])
    }
    return dp[y][x]
}
