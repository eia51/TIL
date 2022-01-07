import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

fun main() {
    val n = nextInt()
    val home = Array(n) { IntArray(3) { nextInt() } }
    val dp = Array(n) { IntArray(3) }

    for (i in 0 until 3) dp[0][i] = home[0][i]

    for (i in 1 until n) {
        dp[i][0] = min(dp[i - 1][1], dp[i - 1][2]) + home[i][0]
        dp[i][1] = min(dp[i - 1][0], dp[i - 1][2]) + home[i][1]
        dp[i][2] = min(dp[i - 1][0], dp[i - 1][1]) + home[i][2]
    }

    val result = min(min(dp[n-1][0], dp[n-1][1]), dp[n-1][2])
    println(result)
    flush()
}


private val br = BufferedReader(InputStreamReader(System.`in`))
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}

private fun flush() {
    br.close()
}
