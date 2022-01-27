import java.util.*

private var n = 0
private var ans = 0
private lateinit var triangle: Array<IntArray>
private lateinit var dp: Array<IntArray>

fun main() {
    input()
    prepro()
    println("$ans\n")
}

private fun input() = with(System.`in`.bufferedReader()) {
    n = readLine().toInt()

    triangle = Array(n) { IntArray(n) }
    dp = Array(n) { IntArray(n) }

    for (i in 0 until n) {
        var j = 0
        val st = StringTokenizer(readLine(), " ")
        while (st.hasMoreTokens())
            triangle[i][j++] = st.nextToken().toInt()
    }
}

private fun prepro() {
    dp[0][0] = triangle[0][0]
    ans = dp[0][0]

    for (i in 1 until n) {
        for (j in 0 until n) {
            val v1 = if (j - 1 < 0) triangle[i][j]
            else dp[i - 1][j - 1] + triangle[i][j]
            val v2 = dp[i - 1][j] + triangle[i][j]
            dp[i][j] = kotlin.math.max(v1, v2)
            if (i == n - 1) ans = kotlin.math.max(ans, dp[i][j])
        }
    }
}
