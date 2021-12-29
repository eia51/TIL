import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val t = nextInt()
    val dp = Array(10001) { IntArray(4) { 0 } }
    dp[1][1] = 1
    dp[2][1] = 1
    dp[2][2] = 1
    dp[3][1] = 1
    dp[3][2] = 1
    dp[3][3] = 1
    for (i in 4..10000) {
        dp[i][1] = dp[i - 1][1]
        dp[i][2] = dp[i - 2][1] + dp[i - 2][2]
        dp[i][3] = dp[i - 3][1] + dp[i - 3][2] + dp[i - 3][3]
    }

    repeat(t) {
        val n = nextInt()
        bw.write("${dp[n][1] + dp[n][2] + dp[n][3]}\n")
    }
    flush()
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))

private fun nextInt(): Int {
    return br.readLine().toInt()
}

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
