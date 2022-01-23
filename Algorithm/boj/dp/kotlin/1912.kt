import java.util.*
import kotlin.math.max

private lateinit var arr: IntArray
private lateinit var dp: IntArray
private var n = 0

fun main() {
    input()
    solve()
}

private fun solve() {
    val bw = System.out.bufferedWriter()

    var maxValue = dp[0]
    for (i in 1 until n) {
        dp[i] = max(dp[i - 1] + arr[i], arr[i])
        maxValue = max(maxValue, dp[i])
    }

    bw.write("$maxValue\n")
    bw.close()
}

private fun input() {
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(br.readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    arr = IntArray(n) { nextInt() }
    dp = IntArray(n)
    dp[0] = arr[0]

    br.close()
}
