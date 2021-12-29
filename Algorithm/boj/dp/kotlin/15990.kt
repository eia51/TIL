import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val mod = 1_000_000_009
    val max = 100000
    val dp = Array(max + 1) { LongArray(4) { 0 } }
    dp[1][1] = 1
    dp[2][2] = 1
    dp[3][1] = 1
    dp[3][2] = 1
    dp[3][3] = 1
    for (i in 4..max) {
        dp[i][1] = (dp[i - 1][2] + dp[i - 1][3]) % mod
        dp[i][2] = (dp[i - 2][1] + dp[i - 2][3]) % mod
        dp[i][3] = (dp[i - 3][1] + dp[i - 3][2]) % mod
    }

    val t = nextInt()
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
