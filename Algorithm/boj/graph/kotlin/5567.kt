import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    lateinit var graph: Array<ArrayList<Int>>
    lateinit var visit: BooleanArray
    val s = HashSet<Int>()
    var n = 0
    var m = 0

    fun getInput() {
        n = readLine().toInt()
        m = readLine().toInt()
        graph = Array(n + 1) { ArrayList() }
        visit = BooleanArray(n + 1)
        repeat(m) {
            val token = StringTokenizer(readLine(), " ")
            val a = token.nextToken().toInt()
            val b = token.nextToken().toInt()
            graph[a].add(b)
            graph[b].add(a)
        }
    }

    fun dfs(curr: Int, cnt: Int) {
        if (cnt > 2)
            return

        s.add(curr)
        for (next in graph[curr]) {
            if (!visit[next]) {
                visit[next] = true
                dfs(next, cnt + 1)
                visit[next] = false
            }
        }
    }

    getInput()
    visit[1] = true
    dfs(1, 0)
    print(s.size - 1)
}
