fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val dp = IntArray(n + 1) { it }

    for (i in 1..n) {
        for (j in 1 until i) {
            if (j * j > i) break
            dp[i] = kotlin.math.min(dp[i], dp[i - j * j] + 1)
        }
    }
    println(dp[n])
}
