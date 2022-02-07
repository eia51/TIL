import java.util.*

private lateinit var graph: Array<ArrayList<Int>>
private lateinit var visit: BooleanArray
private var n = 0
private var m = 0
private var start = 0
private var end = 0
private var dist = 0

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
    start = nextInt()
    end = nextInt()
    m = nextInt()

    graph = Array(n + 1) { ArrayList() }
    visit = BooleanArray(n + 1)
    repeat(m) {
        val v1 = nextInt()
        val v2 = nextInt()
        graph[v1].add(v2)
        graph[v2].add(v1)
    }
}

private fun solve() {
    dfs(start, 0)

    if (dist == 0) println(-1)
    else println(dist)
}

private fun dfs(curr: Int, hop: Int) {
    visit[curr] = true
    if (curr == end) {
        dist = if (dist == 0) hop
        else kotlin.math.min(dist, hop)
        return
    }

    for (next in graph[curr]) {
        if (!visit[next])
            dfs(next, hop + 1)
    }
}
