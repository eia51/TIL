import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private const val max = 1000
private const val mod = 1_000_000_009
private val dp = Array(max + 1) { IntArray(max + 1) }

fun main() {
    prepro()
    var t = nextInt()
    while (t-- > 0)
        pro(nextInt(), nextInt())
    flush()
}

private fun prepro() {
    dp[1][1] = 1
    dp[2][1] = 1; dp[2][2] = 1
    dp[3][1] = 1; dp[3][2] = 2; dp[3][3] = 1
    for (n in 4..max)
        for (m in 2..n)
            dp[n][m] = ((dp[n - 1][m - 1] + dp[n - 2][m - 1]) % mod + dp[n - 3][m - 1]) % mod
}

private fun pro(n: Int, m: Int) {
    var ans = 0
    for (i in m downTo 1) {
        if (dp[n][i] == 0) break
        ans += dp[n][i]
        ans %= mod
    }
    bw.write("$ans\n")
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private var st = StringTokenizer("")

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}
