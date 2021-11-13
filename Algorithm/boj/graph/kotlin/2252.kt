import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

// 위상정렬 : '사이클이 형성 되지 않은 방향 그래프'를 순서 중복 없이 나열하는 것.
// ref -> https://www.acmicpc.net/problem/2252
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var tk = StringTokenizer(readLine(), " ")
    val n = tk.nextToken().toInt()
    val m = tk.nextToken().toInt()

    // 그래프
    val graph = Array(n + 1) { ArrayList<Int>() }

    // 진입차수
    val indegree = IntArray(n + 1) { 0 }

    // 입력
    repeat(m) {
        tk = StringTokenizer(readLine(), " ")
        val head = tk.nextToken().toInt()
        val tail = tk.nextToken().toInt()

        graph[head].add(tail)
        indegree[tail] += 1
    }

    // 진입차수가 0인 노드를 모두 큐에 등록한다.
    val q = ArrayDeque<Int>()
    for (i in 1..n) {
        if (indegree[i] == 0) {
            q.addLast(i)
        }
    }

    val result = StringBuilder()
    while (!q.isEmpty()) {
        val item = q.removeFirst()
        result.append("$item ")

        for (i in graph[item]) {
            indegree[i] -= 1
            if (indegree[i] == 0)
                q.addLast(i)
        }
    }
    println(result)
}
