import java.util.*

private lateinit var tree: Array<ArrayList<Int>>
private lateinit var dp: IntArray
private var n = 0
private var r = 0
private var q = 0

fun main() {
    n = nextInt()
    r = nextInt()
    q = nextInt()

    //make tree
    tree = Array(n + 1) { ArrayList<Int>() }
    repeat(n - 1) {
        val v1 = nextInt()
        val v2 = nextInt()
        tree[v1].add(v2)
        tree[v2].add(v1)
    }

    //make dp for dfs
    dp = IntArray(n + 1) { 1 }
    dfs(r, 0)

    //build answer
    val sb = StringBuilder()
    repeat(q) {
        val u = nextInt()
        sb.append("${dp[u]}\n")
    }
    println(sb)
    br.close()
}

private fun dfs(curr: Int, prev: Int) {
    var childs = 1
    for (next in tree[curr])
        if (next != prev) {
            dfs(next, curr)
            childs += dp[next]
        }

    dp[curr] = childs
}

private val br = System.`in`.bufferedReader()
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}
