package dp

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private lateinit var dp: LongArray 
private const val mod = 1000000009

fun main() {
    val t = nextInt()
    dp = LongArray(1000001)
    dp[1] = 1
    dp[2] = 2
    dp[3] = 4
    for (i in 4..1000000)
        dp[i] = (dp[i-1] + dp[i-2] + dp[i-3]) % mod

    repeat(t) {
        bw.write("${dp[nextInt()]}\n")
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
