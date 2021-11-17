import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

lateinit var board: Array<IntArray>
lateinit var copy: Array<IntArray>

val bx = intArrayOf(0, 0, 1, -1)
val by = intArrayOf(1, -1, 0, 0)

var transition = 0
var minTransition = Int.MAX_VALUE

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()

    board = Array(n) { IntArray(m) }
    copy = Array(n) { IntArray(m) }

    val zero = ArrayList<Pt>()
    val virus = ArrayList<Pt>()

    // 문제 입력 받기
    for (i in 0 until n) {
        token = StringTokenizer(readLine(), " ")
        for (j in 0 until m) {
            board[i][j] = token.nextToken().toInt()
            if (board[i][j] == 0) {
                zero.add(Pt(i, j))
            } else if (board[i][j] == 2) {
                virus.add(Pt(i, j))
            }
        }
    }

    // 0에 대한 좌표를 Point 배열로 저장하고 있는데,
    // 이 중 3개의 좌표를 선택하기 위한 완전탐색을 진행한다.
    // 완전탐색 도중 백트래킹을 활용하여 연산 횟수를 최적화 하도록 한다.
    val len = zero.size
    for (i in 0 until len - 2) {
        for (j in i + 1 until len - 1) {
            for (k in j + 1 until len) {
                copyMap(n, m)
                transition = 0

                //벽을 세운다
                setWall(zero, i, j, k)

                // 가지고 있는 바이러스를 dfs를 활용하여 전파시킨다
                for (v in virus) {
                    dfs(v.x, v.y, n, m)

                    //전파 카운트가 음수라면, 유망하지 않은 조건
                    //더이상의 탐색은 무의미하기 때문에 다음 조건(벽 세우기 경우의 수)으로 넘어간다.
                    if (transition > minTransition)
                        break
                }
                if (transition > minTransition)
                    continue

                // 최소 감염 값을 기록
                minTransition = min(transition, minTransition)
            }
        }
    }
    println(len - minTransition - 3)
}

// 탐색을 위한 배열을 초기화한다
fun copyMap(n: Int, m: Int) {
    for (i in 0 until n) {
        for (j in 0 until m) {
            copy[i][j] = board[i][j]
        }
    }
}

// 벽을 세우고 허무는 작업을 수행한다
fun setWall(zero: ArrayList<Pt>, i: Int, j: Int, k: Int) {
    copy[zero[i].x][zero[i].y] = 1
    copy[zero[j].x][zero[j].y] = 1
    copy[zero[k].x][zero[k].y] = 1
}

fun dfs(x: Int, y: Int, n: Int, m: Int) {
    for (i in 0 until 4) {
        val nx = x + bx[i]
        val ny = y + by[i]

        // 범위 유효성 검사
        if (nx < 0 || ny < 0 || nx >= n || ny >= m || copy[nx][ny] != 0)
            continue

        copy[nx][ny] = 2
        transition++

        // 더이상 탐색 할 필요가 없을 때 강제로 탐색을 종료한다
        if (transition >= minTransition)
            return

        dfs(nx, ny, n, m)
    }
}

// 좌표를 저장하기 위한 Pair 클래스
// Pair를 사용하면 불필요한 서브메소드까지 가져오므로 메모리 낭비가 심하기 때문에 좌표 클래스를 직접 정의
class Pt(val x: Int, val y: Int)
