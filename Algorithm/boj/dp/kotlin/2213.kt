import java.util.*

private var n = 0
private lateinit var weight: IntArray
private lateinit var dp: Array<IntArray>
private lateinit var tree: Array<ArrayList<Int>>
private lateinit var back: Array<Array<ArrayList<Int>>>

fun main() {
    getInput()
    solve()
    flush()
}

private fun getInput() {
    n = nextInt()
    dp = Array(n + 1) { IntArray(2) }
    back = Array(n + 1) { Array(2) { ArrayList() } }

    weight = IntArray(n + 1)
    for (i in 1..n)
        weight[i] = nextInt()

    tree = Array(n + 1) { ArrayList() }
    repeat(n - 1) {
        val v1 = nextInt()
        val v2 = nextInt()
        tree[v1].add(v2)
        tree[v2].add(v1)
    }
}

private fun solve() {
    val root = 1
    dfs(root, 0)

    if (dp[root][0] > dp[root][1])
        printResult(root, 0)
    else
        printResult(root, 1)
}

private fun dfs(curr: Int, prev: Int) {
    dp[curr][0] = weight[curr]
    back[curr][0].add(curr)

    for (next in tree[curr]) {
        if (prev == next)
            continue

        dfs(next, curr)
        dp[curr][0] += dp[next][1]
        back[curr][0].addAll(back[next][1])

        if (dp[next][0] > dp[next][1]) {
            dp[curr][1] += dp[next][0]
            back[curr][1].addAll(back[next][0])
        } else {
            dp[curr][1] += dp[next][1]
            back[curr][1].addAll(back[next][1])
        }
    }
}

private fun printResult(root: Int, idx: Int) {
    bw.write("${dp[root][idx]}\n")
    back[root][idx].sort()

    for (item in back[root][idx])
        bw.write("$item ")

    bw.write("\n")
}

private val br = System.`in`.bufferedReader()
private val bw = System.out.bufferedWriter()
private var st = StringTokenizer("")

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}
