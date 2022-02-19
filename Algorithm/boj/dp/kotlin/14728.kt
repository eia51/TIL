import java.util.*

private var n = 0
private var t = 0
private lateinit var scores: IntArray
private lateinit var times: IntArray
private lateinit var dp: Array<IntArray>

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
    t = nextInt()

    scores = IntArray(n + 1)
    times = IntArray(n + 1)

    for (i in 1..n) {
        times[i] = nextInt()
        scores[i] = nextInt()
    }

    dp = Array(n + 1) { IntArray(t + 1) }
}

private fun solve() {
    for (i in 1..n) {
        for (j in 1..t) {
            if (j >= times[i])
                dp[i][j] = kotlin.math.max(dp[i - 1][j], dp[i - 1][j - times[i]] + scores[i])
            else
                dp[i][j] = dp[i - 1][j]
        }
    }
    println(dp[n][t])
}
