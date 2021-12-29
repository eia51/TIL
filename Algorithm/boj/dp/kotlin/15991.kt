import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val mod = 1_000_000_009
    val max = 100_000
    val dp = LongArray(max + 1) { 0 }
    dp[1] = 1
    dp[2] = 2
    dp[3] = 2
    dp[4] = 3
    dp[5] = 3
    dp[6] = 6
    for (i in 7..max) {
        dp[i] = (dp[i-2] + dp[i-4] + dp[i-6]) % mod
    }

    val t = nextInt()
    repeat(t) {
        val n = nextInt()
        bw.write("${dp[n]}\n")
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
