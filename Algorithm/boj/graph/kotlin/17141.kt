import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.max
import kotlin.math.min

private val dx = intArrayOf(0, 0, 1, -1)
private val dy = intArrayOf(1, -1, 0, 0)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()

    val arr = Array(n) { IntArray(n) }
    val virus = ArrayList<Node>()
    var targetCount = 0

    // 입력 받기
    for (i in 0 until n) {
        token = StringTokenizer(readLine(), " ")
        for (j in 0 until n) {
            arr[i][j] = token.nextToken().toInt()
            if (arr[i][j] == 0)
                targetCount++
            else if (arr[i][j] == 2) {
                virus.add(Node(i, j, 0))
                targetCount++
            }
        }
    }

    // 선택할 바이러스만큼 빼기
    targetCount -= m

    // 바이러스 조합 생성, bfs 수행
    val dq = ArrayDeque<Node>()
    val combs = Combination().make(virus.size, m)

    // 감염 된 블록 수
    var maxTransCount = Int.MIN_VALUE
    // 경과시간
    var minElapsedTime = Int.MAX_VALUE

    // 활성 바이러스 조합에 따른 bfs 검사
    for (comb in combs) {
        val visit = Array(n) { BooleanArray(n) }
        var elapsedTime = 0
        var transCount = 0

        //선택 된 활성바이러스 조합 큐에 추가
        // 활성바이러스 마킹
        for (i in comb) {
            dq.add(virus[i])
            visit[virus[i].x][virus[i].y] = true
        }

        // bfs 수행
        while (!dq.isEmpty()) {
            val node = dq.poll()
            val next = node.time + 1

            for (i in 0 until 4) {
                val nx = node.x + dx[i]
                val ny = node.y + dy[i]

                if (nx < 0 || ny < 0 || nx >= n || ny >= n || visit[nx][ny] || arr[nx][ny] == 1)
                    continue

                dq.add(Node(nx, ny, next))
                visit[nx][ny] = true

                // 새로운 블록이 0인 경우만 감염 카운팅
                transCount++
                elapsedTime = next
            }
        }

        // 유효한 결과에 대해서만 수행결과를 기록
        if (targetCount == transCount) {
            maxTransCount = max(maxTransCount, transCount)
            minElapsedTime = min(minElapsedTime, elapsedTime)
        }
    }

    // 결과 출력
//    println("max=$maxTransCount target=$targetCount")
    if (maxTransCount < targetCount) {
        println(-1)
    } else {
        println(minElapsedTime)
    }
}

// 좌표 정보를 저장하는 클래스
private class Node(val x: Int, val y: Int, var time: Int)

// 0~n-1개의 숫자가 들어있는 배열에서 r개로 이뤄진 조합을 뽑아내는 클래스
private class Combination {
    private var comb = ArrayList<ArrayList<Int>>()

    fun make(n: Int, r: Int): ArrayList<ArrayList<Int>> {
        val arr = IntArray(n) { it }
        val visit = BooleanArray(n)

        combination(arr, visit, 0, n, r)
        return comb
    }

    private fun combination(arr: IntArray, visit: BooleanArray, start: Int, n: Int, r: Int) {
        if (r == 0) {
            val tmp = ArrayList<Int>()
            for (i in 0 until n)
                if (visit[i]) tmp.add(arr[i])
            comb.add(tmp)
        }

        for (i in start until n) {
            visit[i] = true
            combination(arr, visit, i + 1, n, r - 1)
            visit[i] = false
        }
    }
}
