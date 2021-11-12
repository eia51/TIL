import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.max

//ref -> https://www.acmicpc.net/problem/7576
val q: Queue<Tomato> = LinkedList()
val dx = intArrayOf(0, 0, -1, 1)
val dy = intArrayOf(1, -1, 0, 0)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var tk = StringTokenizer(readLine(), " ")
    val m = tk.nextToken().toInt()
    val n = tk.nextToken().toInt()

    // 입력
    var zero = 0
    val arr = Array(n) { IntArray(m) { 0 } }
    for (i in 0 until n) {
        tk = StringTokenizer(readLine(), " ")
        for (j in 0 until m) {
            arr[i][j] = tk.nextToken().toInt()
            if (arr[i][j] == 0)
                zero++
        }
    }

    // 검사 할 필요가 없음
    if (zero == 0) {
        println(0)
        return
    }

    // 진입점 추가
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (arr[i][j] == 1)
                q.add(Tomato(i, j))
        }
    }

    // bfs
    val result = bfs(n, m, arr)

    // 결과 출력
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (arr[i][j] == 0) {
                println(-1)
                return
            }
        }
    }
    println(result - 1)
}

private fun bfs(n: Int, m: Int, arr: Array<IntArray>): Int {
    var result = 0
    while (!q.isEmpty()) {
        val p = q.poll()
        val row = p.row
        val col = p.col

        for (i in 0 until 4) {
            val nextRow = row + dx[i]
            val nextCol = col + dy[i]

            if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= m)
                continue

            if (arr[nextRow][nextCol] == 0) {
                q.add(Tomato(nextRow, nextCol))
                arr[nextRow][nextCol] = arr[row][col] + 1

                result = max(result, arr[nextRow][nextCol])
            }
        }
    }
    return result
}

class Tomato(val row: Int, val col: Int)
