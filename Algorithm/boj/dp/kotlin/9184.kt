import java.util.*

private val dp = HashMap<String, Int>()

fun main() {
    while (true) {
        val a = nextInt()
        val b = nextInt()
        val c = nextInt()

        if (a == -1 && b == -1 && c == -1)
            break

        bw.write("w($a, $b, $c) = ${w(a, b, c)}\n")
    }

    flush()
}

fun w(a: Int, b: Int, c: Int): Int {
    val key = "${a}_${b}_${c}"
    if (dp.containsKey(key)) {
        return dp[key]!!
    }
    if (a <= 0 || b <= 0 || c <= 0) {
        dp[key] = 1
        return dp[key]!!
    }
    if (a > 20 || b > 20 || c > 20) {
        dp[key] = w(20, 20, 20)
        return dp[key]!!
    }
    if (b in (a + 1) until c) {
        dp[key] = w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c)
        return dp[key]!!
    }
    dp[key] = w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1)
    return dp[key]!!
}

private val br = System.`in`.bufferedReader()
private val bw = System.out.bufferedWriter()
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(readLine(), " ")
    return st.nextToken().toInt()
}

private fun flush() {
    br.close()
    bw.flush()
    bw.close()
}
