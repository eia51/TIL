fun main() = with(System.`in`.bufferedReader()){
    val bw = System.out.bufferedWriter()

    val dp = LongArray(101)
    dp[1] = 1; dp[2] = 1; dp[3] = 1; dp[4] = 2
    for (i in 5..100)
        dp[i] = dp[i-1] + dp[i-5]

    var t = readLine().toInt()
    while(t-- > 0) {
        val n = readLine().toInt()
        bw.write("${dp[n]}\n")
    }
    bw.flush()
    bw.close()
}
