import java.util.*

private lateinit var arr: Array<IntArray>
private lateinit var dp: Array<LongArray>
private var n = 0

fun main() {
    input()
    println(solve(0, 0))
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
    dp = Array(n) { LongArray(n) { -1 } }
}

private fun solve(i: Int, j: Int): Long {
    if (i == n -1 && j == n - 1)
        return 1

    if (dp[i][j] != -1L)
        return dp[i][j]

    dp[i][j] = 0
    val ni = i + arr[i][j]
    if (ni in 0 until n)
        dp[i][j] += solve(ni, j)

    val nj = j + arr[i][j]
    if (nj in 0 until n)
        dp[i][j] += solve(i, nj)

    return dp[i][j]
}
