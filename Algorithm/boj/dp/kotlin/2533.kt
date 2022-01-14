import java.util.*
import kotlin.math.min

private var n = 0
private lateinit var dp: Array<IntArray>
private lateinit var tree: Array<ArrayList<Int>>

fun main() {
    getInput()

    val root = 1
    dfs(root, 0)

    println(min(dp[root][0], dp[root][1]))
}

private fun dfs(curr: Int, prev: Int) {
    dp[curr][0] = 1

    for (next in tree[curr]) {
        if (prev == next) continue

        dfs(next, curr)

        dp[curr][0] += min(dp[next][0], dp[next][1])
        dp[curr][1] += dp[next][0]
    }
}

private fun getInput() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    tree = Array(n + 1) { ArrayList() }
    dp = Array(n + 1) { IntArray(2) }

    repeat(n - 1) {
        val v1 = nextInt()
        val v2 = nextInt()
        tree[v1].add(v2)
        tree[v2].add(v1)
    }
}
