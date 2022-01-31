import java.util.*
import kotlin.math.max

private lateinit var arr: IntArray
private lateinit var dp: IntArray
private var n = 0

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    arr = IntArray(n) { nextInt() }
    dp = IntArray(n)
    dp[0] = arr[0]
}

private fun solve() {
    var result = dp[0]
    for (i in 1 until n) {
        for (j in 0 until i) {
            if (arr[i] > arr[j])
                dp[i] = max(dp[i], dp[j] + arr[i])
            else
                dp[i] = max(dp[i], arr[i])

            result = max(result, dp[i])
        }
    }
    println(result)
}
