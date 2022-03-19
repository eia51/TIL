import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    lateinit var graph: Array<ArrayList<Int>>
    lateinit var visit: BooleanArray
    lateinit var parent: IntArray
    var n = 0

    fun getInput() {
        n = readLine().toInt()
        graph = Array(n + 1) { ArrayList<Int>() }
        visit = BooleanArray(n + 1)
        parent = IntArray(n + 1)
        repeat(n - 1) {
            val token = StringTokenizer(readLine(), " ")
            val a = token.nextToken().toInt()
            val b = token.nextToken().toInt()
            graph[a].add(b)
            graph[b].add(a)
        }
    }

    fun dfs(curr: Int, prev: Int) {
        visit[curr] = true
        parent[curr] = prev
        for (next in graph[curr]) {
            if (!visit[next])
                dfs(next, curr)
        }
    }

    fun printResult() {
        val bw = BufferedWriter(OutputStreamWriter(System.out))
        for (i in 2..n) {
            bw.write("${parent[i]}\n")
        }
        bw.flush()
        bw.close()
    }

    getInput()
    dfs(1, 1)
    printResult()
}
