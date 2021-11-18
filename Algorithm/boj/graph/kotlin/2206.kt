import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

val dx = intArrayOf(0, 0, 1, -1)
val dy = intArrayOf(1, -1, 0, 0)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()

    val arr = Array(n) { IntArray(m) }
    for (i in 0 until n) {
        val tmp = readLine()
        var j = 0
        for (ch in tmp)
            arr[i][j++] = ch.code - 48
    }

    val result = if (n == 1 && m == 1) 1 else dfs(arr, n, m)
    println(result)
}

fun dfs(arr: Array<IntArray>, n: Int, m: Int): Int {
    val dq = ArrayDeque<Player>()
    val visit = Array(n) { BooleanArray(m) { false } }
    val visitAfterBomb = Array(n) { BooleanArray(m) { false } }
    val bombed = Array(n) { BooleanArray(m) { false } }

    var minCnt = Int.MAX_VALUE
    dq.addLast(Player(0, 0, 1, true))
    visit[0][0] = true

    var winner: Player? = null
    while (!dq.isEmpty()) {
        val item = dq.poll()

        for (i in 0 until 4) {
            val nx = item.x + dx[i]
            val ny = item.y + dy[i]

            if (nx < 0 || ny < 0 || nx >= n || ny >= m)
                continue

            if (nx == n - 1 && ny == m - 1 && (item.cnt < minCnt)) {
                winner = item
                winner.cnt += 1
                minCnt = winner.cnt
            }

            if (arr[nx][ny] == 1 && !bombed[nx][ny] && item.hasBomb) {
                dq.addLast(Player(nx, ny, item.cnt + 1, false))
                setPlayerVisit(visit, visitAfterBomb, nx, ny, item)
                bombed[nx][ny] = true
            }

            if (arr[nx][ny] == 0 && !isPlayerVisit(visit, visitAfterBomb, nx, ny, item)) {
                dq.addLast(Player(nx, ny, item.cnt + 1, item.hasBomb))
                setPlayerVisit(visit, visitAfterBomb, nx, ny, item)
            }
        }
    }

    if (winner != null)
        return winner.cnt

    return -1
}

fun isPlayerVisit(visit: Array<BooleanArray>, visitAfterBomb: Array<BooleanArray>, x: Int, y: Int, item: Player): Boolean {
    return if (item.hasBomb) {
        visitAfterBomb[x][y]
    } else {
        visit[x][y]
    }
}

fun setPlayerVisit(visit: Array<BooleanArray>, visitAfterBomb: Array<BooleanArray>, x: Int, y: Int, item: Player) {
    if (item.hasBomb) {
        visitAfterBomb[x][y] = true
    } else {
        visit[x][y] = true
    }
}

class Player(val x: Int, val y: Int, var cnt: Int, val hasBomb: Boolean)
