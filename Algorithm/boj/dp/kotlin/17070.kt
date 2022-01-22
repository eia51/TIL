import java.util.*

private lateinit var dp: Array<Array<IntArray>>
private lateinit var arr: Array<BooleanArray>
private var n = 0

private val dy = intArrayOf(0, 1, 1)
private val dx = intArrayOf(1, 0, 1)
private val direction = Array(3) { ArrayList<Int>() }

fun main() {
    input()
    solve()
}

private fun solve() {
    for (i in 0 until n) {
        for (j in 1 until n) {
            for (k in 0 until 3) {
                if (!arr[i][j] || dp[i][j][k] == 0)
                    continue

                check(i, j, k)
            }
        }
    }

    var result = 0
    for (i in 0 until 3)
        result += dp[n - 1][n - 1][i]

    println(result)
}

private fun check(y: Int, x: Int, d: Int) {
    for (dir in direction[d]) {
        val ny = y + dy[dir]
        val nx = x + dx[dir]

        if (ny !in 0 until n || nx !in 0 until n || !arr[ny][nx])
            continue

        // 대각선 X
        if (dir != 2)
            dp[ny][nx][dir] += dp[y][x][d]
        // 대각선
        else if (arr[ny - 1][nx] && arr[ny][nx - 1])
            dp[ny][nx][dir] += dp[y][x][d]
    }
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    arr = Array(n) { BooleanArray(n) { nextInt() == 0 } }
    dp = Array(n) { Array(n) { IntArray(3) } }

    //0 가로, 1 세로, 2 대각
    direction[0] = arrayListOf(0, 2)
    direction[1] = arrayListOf(1, 2)
    direction[2] = arrayListOf(0, 1, 2)

    dp[0][1][0] = 1
}
