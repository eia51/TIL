import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private const val max = 100_000
private const val mod = 1_000_000_009
private val dp = Array(max + 1) { IntArray(2) }

fun main() {
    prepro()

    var t = nextInt()
    while (t-- > 0)
        pro(nextInt())

    flush()
}

private fun prepro() {
    dp[1][0] = 0; dp[1][1] = 1
    dp[2][0] = 1; dp[2][1] = 1
    dp[3][0] = 2; dp[3][1] = 2

    for (i in 4..max) {
        dp[i][0] = ((dp[i - 1][1] + dp[i - 2][1]) % mod + dp[i - 3][1]) % mod
        dp[i][1] = ((dp[i - 1][0] + dp[i - 2][0]) % mod + dp[i - 3][0]) % mod
    }
}

private fun pro(n: Int) {
    bw.write("${dp[n][1]} ${dp[n][0]}\n")
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    return br.readLine().toInt()
}
