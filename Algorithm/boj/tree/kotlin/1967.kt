import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private val tree = HashMap<Int, ArrayList<Edge>>()
private var n = 0
private var totalDistance = 0
private var longestDistance = Int.MIN_VALUE
private var farthestEdge = -1

fun main() {
    getInput()
    solve()
    flush()
}

private fun getInput() {
    n = nextInt()
    repeat(n - 1) {
        val v1 = nextInt()
        val v2 = nextInt()
        val d = nextInt()
        if (!tree.containsKey(v1)) tree[v1] = ArrayList()
        if (!tree.containsKey(v2)) tree[v2] = ArrayList()

        tree[v1]!!.add(Edge(v2, d))
        tree[v2]!!.add(Edge(v1, d))
    }
}

private fun solve() {
    init()
    var edge = Edge(1, 0)
    dfs(edge, edge)

    init()
    edge = Edge(farthestEdge, 0)
    dfs(edge, edge)

    bw.write("$longestDistance\n")
}

private fun init() {
    totalDistance = 0
    longestDistance = Int.MIN_VALUE
}

private fun dfs(curr: Edge, prev :Edge) {
    totalDistance += curr.d

    if (longestDistance < totalDistance) {
        longestDistance = totalDistance
        farthestEdge = curr.v
    }

    tree[curr.v]?.forEach {
        if (it.v != prev.v) dfs(it, curr)
    }
    totalDistance -= curr.d
}

private data class Edge(val v: Int, val d: Int)

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
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
