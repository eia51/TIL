fun main() = with(System.`in`.bufferedReader()) {
    val mod = 10_007
    val n = readLine().toInt()
    if (n == 1) {
        println(1)
        return
    }

    val dp = IntArray(n + 1)
    dp[1] = 1
    dp[2] = 3

    for (i in 3..n)
        dp[i] = (dp[i-1] + dp[i-2] * 2) % mod

    println(dp[n])
}
