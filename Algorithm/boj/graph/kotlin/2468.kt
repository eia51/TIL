import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.max

lateinit var board: Array<IntArray>

val goX = intArrayOf(0, 0, 1, -1)
val goY = intArrayOf(1, -1, 0, 0)

var area = 1
var maxArea = area

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    board = Array(n) { IntArray(n) }
    val heightSet = HashSet<Int>()

    // 비의 높이 -> 0 ~ 99
    // 땅의 높이가 1 ~ 100, 모두 잠기는 100은 의미 없다, 잠기지 않는 경우가 있기 때문에 0을 포함한다.
    for (i in 0 until n) {
        val token = StringTokenizer(readLine(), " ")
        for (j in 0 until n) {
            board[i][j] = token.nextToken().toInt()
            heightSet.add(board[i][j])
        }
    }

    // 검사 할 비의 높이 목록 만들기
    // 모든 높이를 계산할 필요 없다.
    // 실질적으로 땅이 잠기는건 땅의 높이만큼 비가 왔을때니,
    // 전체 땅의 높이 목록을 가지고 있다가, 그 높이만큼만 비를 내려 검사하면 된다.
    val heightArray = heightSet.toIntArray()
    for (rainIdx in 0 until heightArray.size-1) {
        val visit = Array(n) { BooleanArray(n) }
        val rain = heightArray[rainIdx]
        area = 0

        for (i in 0 until n)
            for (j in 0 until n)
                if (!visit[i][j] && board[i][j] > rain) {
                    dfs(rain, i, j, n, visit)
                    area++
                }

        maxArea = max(area, maxArea)
    }

    println(maxArea)
}

private fun dfs(rain:Int, x: Int, y: Int, n: Int, visit:Array<BooleanArray>) {
    visit[x][y] = true
    for (i in 0 until 4) {
        val nx = x + goX[i]
        val ny = y + goY[i]

        if (nx < 0 || ny < 0 || nx >= n || ny >= n || visit[nx][ny] || board[nx][ny] <= rain)
            continue

        dfs(rain, nx, ny, n, visit)
    }
}
