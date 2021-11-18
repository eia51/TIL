import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()

    val graph = Array(n + 1) { ArrayList<Int>() }
    for (i in 0 until m) {
        token = StringTokenizer(readLine(), " ")
        val p = token.nextToken().toInt()
        val c = token.nextToken().toInt()
        graph[p].add(c)
        graph[c].add(p)
    }

    val visit = BooleanArray(n + 1) { false }
    var count = 0
    for (i in 1..n) {
        if (dfs(graph, visit, i))
            count++
    }
    println(count)
}

fun dfs(graph: Array<ArrayList<Int>>, visit: BooleanArray, start: Int): Boolean {
    if (visit[start])
        return false

    val dq = ArrayDeque<Int>()
    dq.addLast(start)
    visit[start] = true
    while (!dq.isEmpty()) {
        val i = dq.poll()
        for (child in graph[i]) {
            if (!visit[child]) {
                dq.addLast(child)
                visit[child] = true
            }
        }
    }

    return true
}
