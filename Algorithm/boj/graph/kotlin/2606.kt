import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val m = readLine().toInt()

    val graph = Array(n + 1) { ArrayList<Int>() }
    val visit = BooleanArray(n + 1) { false }
    for (i in 0 until m) {
        val tk = StringTokenizer(readLine(), " ")
        val p = tk.nextToken().toInt()
        val c = tk.nextToken().toInt()
        graph[p].add(c)
        graph[c].add(p)
    }

    val res = dfs(graph, 1, visit)
    println(res)
}

fun dfs(arr: Array<ArrayList<Int>>, start: Int, visit: BooleanArray): Int {
    var count = 0
    val dq = ArrayDeque<Int>()

    dq.addLast(start)
    visit[start] = true

    while (!dq.isEmpty()) {
        val item = dq.poll()
        for (i in arr[item]) {
            if (!visit[i]) {
                dq.addLast(i)
                visit[i] = true
                count += 1
            }
        }
    }
    return count
}
