import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*


fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    lateinit var graph: Array<ArrayList<Int>>
    lateinit var visit: BooleanArray
    lateinit var dist: IntArray
    
    var findCycle = false
    var keepValue = false
    var startPos = 0
    var n = 0
    
    // 1) 입력
    fun input() {
        n = readLine().toInt()
        graph = Array(n + 1) { ArrayList<Int>() }
        visit = BooleanArray(n + 1)
        dist = IntArray(n + 1)

        repeat(n) {
            val token = StringTokenizer(readLine(), " ")
            val a = token.nextToken().toInt()
            val b = token.nextToken().toInt()
            graph[a].add(b)
            graph[b].add(a)
        }
    }

    // 2) 사이클을 찾기 위한 dfs
    fun dfs(curr: Int, prev: Int) {
        visit[curr] = true

        // 한쪽 방향으로 탐색을 수행하면서 사이클이 생성됐는지 검사
        for (next in graph[curr]) {
            // 단방향 순회에서 다음에 가야할 노드가 방문 되어있다면?
            // 사이클이 생성 된 것
            // 다음 노드가 바로 사이클의 시작점!
            // dfs를 종료한다. (함수 stack에 있는 dfs를 모두 종료해야하기 때문에 findCycle 플래그를 사용한다.)
            if (next != prev && visit[next]) {
                startPos = next
                findCycle = true
                dist[curr] = -1
                return
            }
            // 방문하지 않은 노드에 대해선 dfs를 추가 수행해준다
            else if (!visit[next]) {
                dfs(next, curr)
            }

            // 이전 dfs로 사이클을 찾았다면, 해당 부분부터 사이클이 시작 된 지점까지 백트래킹하면서 값을 -1로 초기화한다.
            if (findCycle) {
                if (!keepValue)
                    dist[curr] = -1
                if (startPos == curr)
                    keepValue = true
                // 사이클을 찾았으므로 이 조건문을 감싸는 반복문도 필요가 없다.
                return
            }
        }
    }

    // 3) 사이클이 아닌 부분의 거리를 계산한다
    fun dfsDist(curr: Int, prev: Int) {
        visit[curr] = true

        //dist[n] = -1인 부분은 사이클이므로 무시,
        // 그 외의 경우에 사이클로부터 거리가 얼마나 떨어져있는지 갱신해준다
        if (dist[curr] != -1) {
            // 사이클로부터 인접한 경우 = 1
            if (dist[prev] == -1)
                dist[curr] = 1
            // 그 외 경우 ++
            else
                dist[curr] = dist[prev] + 1
        }

        // 인접한 노드들에 대해서 dfs를 이어서 수행한다
        for (next in graph[curr])
            if (!visit[next])
                dfsDist(next, curr)
    }

    // 4) 거리가 저장 된 배열을 출력한다
    // -1로 되어있는 곳은 사이클이므로, -1을 만난다면, 0으로 바꿔 출력해준다.
    fun print() {
        for (i in 1..n) {
            if (dist[i] == -1)
                bw.write("0 ")
            else
                bw.write("${dist[i]} ")
        }
        bw.write("\n")
        bw.flush()
        bw.close()
    }

    input()
    dfs(1, 0)
    visit.fill(false)
    dfsDist(startPos, 0)
    print()
}
