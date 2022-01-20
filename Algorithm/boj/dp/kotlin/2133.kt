fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val dp = Array(31) { IntArray(2) }
    dp[2][0] = 3; dp[2][1] = 3

    for (i in 4..30 step 2) {
        dp[i][0] = 2 + dp[i - 2][0] * 3 + dp[i - 4][1] * 2
        dp[i][1] = dp[i][0] + dp[i - 2][1]
    }

    println(dp[n][0])
}
