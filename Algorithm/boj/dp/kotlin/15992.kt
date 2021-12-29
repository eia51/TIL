import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val mod = 1_000_000_009
    val max = 1000
    val dp = Array(max + 1) { IntArray(max + 1) { 0 } }
    dp[1][1] = 1
    dp[2][1] = 1
    dp[2][2] = 1
    dp[3][1] = 1
    dp[3][2] = 2
    dp[3][3] = 1
    for (n in 4..max) {
        for (m in 2..n) {
            dp[n][m] = ((dp[n - 1][m - 1] + dp[n - 2][m - 1]) % mod + dp[n - 3][m - 1]) % mod
        }
    }

    val t = br.readLine().toInt()
    repeat(t) {
        val nm = br.readLine().split(" ")
        val n = nm[0].toInt()
        val m = nm[1].toInt()
        bw.write("${dp[n][m]}\n")
    }
    flush()
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
