import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    when (val n = readLine().toInt()) {
        1 -> println(3)
        2 -> println(7)
        else -> solve(n)
    }
}

private fun solve(n: Int) {
    val mod = 9901
    val dp = IntArray(n + 1)
    dp[1] = 3
    dp[2] = 7

    for (i in 3..n) {
        dp[i] = (2 * dp[i - 1] + dp[i - 2]) % mod
    }
    println(dp[n])
}
