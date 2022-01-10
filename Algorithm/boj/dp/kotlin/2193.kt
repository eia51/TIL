fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val dp = Array(n) { LongArray(2) }
    dp[0][0] = 0
    dp[0][1] = 1

    for (i in 1 until n) {
        dp[i][0] = dp[i - 1][0] + dp[i - 1][1]
        dp[i][1] = dp[i - 1][0]
    }
    println(dp[n - 1][0] + dp[n - 1][1])
}
