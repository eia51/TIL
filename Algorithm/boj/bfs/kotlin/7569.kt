import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.max

val dh = intArrayOf(1, -1, 0, 0, 0, 0)
val dx = intArrayOf(0, 0, 1, -1, 0, 0)
val dy = intArrayOf(0, 0, 0, 0, 1, -1)

// ref -> https://www.acmicpc.net/problem/7569
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var tk = StringTokenizer(readLine(), " ")
    val m = tk.nextToken().toInt()
    val n = tk.nextToken().toInt()
    val h = tk.nextToken().toInt()

    var zeroCount = 0
    val arr = Array(h) { Array(n) { IntArray(m) } }
    for (i in 0 until h) {
        for (j in 0 until n) {
            tk = StringTokenizer(readLine(), " ")
            for (k in 0 until m) {
                arr[i][j][k] = tk.nextToken().toInt()
                if (arr[i][j][k] == 0)
                    zeroCount++
            }
        }
    }

    // 익을 토마토가 입력상 없는 경우 바로 출력하고 종료
    if (zeroCount == 0) {
        println(0)
        return
    }

    // bfs 연산 수행
    bfs(arr, h, n, m)

    // 출력
    var result = 0
    for (i in 0 until h) {
        for (j in 0 until n) {
            for (k in 0 until m) {
                if (arr[i][j][k] == 0) {
                    println(-1)
                    return
                }

                result = max(result, arr[i][j][k])
            }
        }
    }
    println(result - 1)
}

fun bfs(arr: Array<Array<IntArray>>, h: Int, n: Int, m: Int) {
    val dq = ArrayDeque<Tomato>()

    // 시작지점 덱에 추가
    for (i in 0 until h) {
        for (j in 0 until n) {
            for (k in 0 until m) {
                if (arr[i][j][k] == 1)
                    dq.addLast(Tomato(i, j, k))
            }
        }
    }

    while (!dq.isEmpty()) {
        val tmt = dq.removeFirst()

        for (i in 0 until 6) {
            val nh = tmt.h + dh[i]
            val nx = tmt.x + dx[i]
            val ny = tmt.y + dy[i]

            if (nh < 0 || nh >= h || nx < 0 || nx >= n || ny < 0 || ny >= m || arr[nh][nx][ny] == -1 || arr[nh][nx][ny] != 0)
                continue

            dq.addLast(Tomato(nh, nx, ny))
            arr[nh][nx][ny] = arr[tmt.h][tmt.x][tmt.y] + 1
        }
    }
}

class Tomato(val h: Int, val x: Int, val y: Int)
