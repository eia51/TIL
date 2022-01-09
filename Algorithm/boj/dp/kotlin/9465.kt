import java.util.*
import kotlin.math.max

fun main() {
    var t = br.readLine().toInt()
    while (t-- > 0)
        solve()
    flush()
}

private fun solve() {
    val n = br.readLine().toInt()

    val arr = Array(2) { IntArray(n + 1) }
    val st1 = StringTokenizer(br.readLine(), " ")
    val st2 = StringTokenizer(br.readLine(), " ")
    for (i in 1..n) {
        arr[0][i] = st1.nextToken().toInt()
        arr[1][i] = st2.nextToken().toInt()
    }

    val dp = Array(2) { IntArray(n + 1) }
    dp[0][1] = arr[0][1]
    dp[1][1] = arr[1][1]
    for (i in 2..n) {
        dp[0][i] = max(dp[1][i - 1], dp[1][i - 2]) + arr[0][i]
        dp[1][i] = max(dp[0][i - 1], dp[0][i - 2]) + arr[1][i]
    }
    bw.write("${max(dp[0][n], dp[1][n])}\n")
}

private val br = System.`in`.bufferedReader()
private val bw = System.`out`.bufferedWriter()

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
