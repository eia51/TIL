fun main() {
    val mod = 1_000_000_000
    val br = System.`in`.bufferedReader()
    val bw = System.out.bufferedWriter()

    val n = br.readLine().toInt()
    val dp = Array(n + 1) { IntArray(10) }

    for (i in 1..9)
        dp[1][i] = 1

    for (i in 2..n) {
        for (j in 0..9) {
            dp[i][j] = when (j) {
                0 -> dp[i - 1][1]
                9 -> dp[i - 1][8]
                else -> (dp[i - 1][j - 1] + dp[i - 1][j + 1]) % mod
            }
        }
    }
    var ans = 0
    for (i in 0..9) ans = (ans + dp[n][i]) % mod

    bw.write("$ans\n")

    bw.flush()
    br.close()
    bw.close()
}
