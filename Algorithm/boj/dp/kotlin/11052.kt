import java.util.*

private lateinit var card: IntArray
private lateinit var dp: IntArray
private var n = 0

fun main() {
    input()
    solve()
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

    card = IntArray(n + 1)
    dp = IntArray(n + 1)

    for (i in 1..n) card[i] = nextInt()
}

private fun solve() {
    for (i in 1..n) {
        for (j in 1..i) {
            dp[i] = kotlin.math.max(dp[i], card[j] + dp[i-j])
        }
    }
    println(dp[n])
}
