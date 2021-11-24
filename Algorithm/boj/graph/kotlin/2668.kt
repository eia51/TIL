import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    lateinit var visit: BooleanArray
    lateinit var graph: Array<ArrayList<Int>>
    val result = PriorityQueue<Int>()
    var findCycle = false
    var startPos = 0
    var n = 0

    // 입력 받기
    fun getInput() {
        n = readLine().toInt()
        visit = BooleanArray(n + 1)
        graph = Array(n + 1) { ArrayList() }
        for (i in 0 until n)
            graph[i+1].add(readLine().toInt())
    }

    // 사이클의 시작점 찾기
    fun dfs(curr: Int, target: Int) {
        for (next in graph[curr]) {
            if (visit[next]) {
                startPos = next
                findCycle = true
                return
            } else if (!visit[next]) {
                visit[next] = true
                dfs(next, target)
                visit[next] = false
            }
            if (findCycle) return
        }
    }

    // 사이클의 시작점으로부터 dfs 탐색하면서 사이클에 해당하는 값 추가
    fun dfsPrint(start: Int) {
        visit[start] = true
        result.add(start)

        for (next in graph[start])
            if (!visit[next])
                dfsPrint(next)
    }

    // 답 출력
    fun printAnswer() {
        val bw = BufferedWriter(OutputStreamWriter(System.out))
        bw.write("${result.size}\n")
        while (!result.isEmpty())
            bw.write("${result.poll()}\n")
        bw.flush()
        bw.close()
    }

    fun solve() {
        getInput()
        for (i in 1..n) {
            if (!visit[i]) {

                findCycle = false
                visit[i] = true
                dfs(i, i)
                visit[i] = false

                if (findCycle)
                    if (!visit[startPos])
                        dfsPrint(startPos)
            }
        }
        printAnswer()
    }

    solve()
}
