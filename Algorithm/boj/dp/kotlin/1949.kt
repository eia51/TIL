import java.util.*
import kotlin.math.max

private lateinit var score: IntArray
private lateinit var dp: Array<IntArray>
private lateinit var tree: Array<ArrayList<Int>>
private var n = 0

fun main() {
    input()
    solve()
}

private fun solve() {
    val root = 1
    dfs(root, 0)

    val result = max(dp[0][root], dp[1][root])
    println(result)
}

private fun dfs(curr: Int, prev: Int) {
    dp[0][curr] = score[curr]

    for (next in tree[curr]) {
        if (prev == next)
            continue

        dfs(next, curr)
        dp[0][curr] += dp[1][next]
        dp[1][curr] += max(dp[0][next], dp[1][next])
    }
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    dp = Array(2) { IntArray(n + 1) }

    score = IntArray(n + 1)
    for (i in 1..n)
        score[i] = nextInt()

    tree = Array(n + 1) { ArrayList() }
    repeat(n - 1) {
        val v1 = nextInt()
        val v2 = nextInt()
        tree[v1].add(v2)
        tree[v2].add(v1)
    }
}

