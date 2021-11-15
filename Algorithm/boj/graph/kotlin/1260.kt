import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

// ref -> https://www.acmicpc.net/problem/1260
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()
    val v = token.nextToken().toInt()

    val graph = Array(n + 1) { ArrayList<Int>() }

    for (i in 0 until m) {
        token = StringTokenizer(readLine(), " ")
        val v1 = token.nextToken().toInt()
        val v2 = token.nextToken().toInt()

        //양방향 그래프
        graph[v1].add(v2)
        graph[v2].add(v1)
    }

    val visit = BooleanArray(n + 1)

    dfs(graph, v, visit, bw)
    bw.write("\n")

    bfs(graph, v, visit, bw)
    bw.write("\n")

    bw.flush()
    bw.close()
}

fun dfs(graph: Array<ArrayList<Int>>, v: Int, visit: BooleanArray, bw: BufferedWriter) {
    visit[v] = true
    bw.write("$v ")

    for (i in graph[v].sorted()) {
        if (!visit[i])
            dfs(graph, i, visit, bw)
    }
}

fun bfs(graph: Array<ArrayList<Int>>, v: Int, visit: BooleanArray, bw: BufferedWriter) {
    val q = ArrayDeque<Int>()
    q.addLast(v)
    visit[v] = false
    while (!q.isEmpty()) {
        val item = q.removeFirst()
        bw.write("$item ")
        for (i in graph[item].sorted()) {
            if(visit[i]) {
                q.addLast(i)
                visit[i] = false
            }
        }
    }
}
