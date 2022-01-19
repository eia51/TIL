import java.util.*

private lateinit var arr: IntArray
private lateinit var dp: Array<LongArray>
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
    arr = IntArray(n + 1)
    dp = Array(n) { LongArray(21) }
    for (i in 1..n) arr[i] = nextInt()
}

private fun solve() {
    dp[1][arr[1]] = 1

    var next: Int
    for (i in 2..n - 1) {
        for (j in 0..20) {
            if (dp[i - 1][j] == 0L)
                continue

            next = j + arr[i]
            if (next in 0..20)
                dp[i][next] += dp[i-1][j]

            next = j - arr[i]
            if (next in 0..20)
                dp[i][next] += dp[i-1][j]
        }
    }
    println(dp[n-1][arr[n]])
}
