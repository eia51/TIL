package dp

import java.util.*

private lateinit var time: IntArray
private lateinit var cost: IntArray
private lateinit var dp: IntArray
private var n = 0

fun main() {
    input()
    solve()
}

private fun solve() {
    for (i in n - 1 downTo 0) {
        if (i + time[i] > n)
            dp[i] = dp[i + 1]
        else
            dp[i] = kotlin.math.max(dp[i + 1], cost[i] + dp[i + time[i]])
    }

    println(dp[0])
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")

        return st.nextToken().toInt()
    }

    n = nextInt()
    time = IntArray(n)
    cost = IntArray(n)
    dp = IntArray(n + 1)

    for (i in 0 until n) {
        time[i] = nextInt()
        cost[i] = nextInt()
    }
}
