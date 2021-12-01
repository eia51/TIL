import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    lateinit var board: Array<IntArray>
    lateinit var rect: Array<IntArray>
    lateinit var visit: Array<BooleanArray>

    val dx = intArrayOf(-1, 1, 0, 0)
    val dy = intArrayOf(0, 0, -1, 1)
    val result = ArrayList<Int>()
    var area = 0
    var m = 0
    var n = 0
    var k = 0

    fun getInput() {
        var token = StringTokenizer(readLine(), " ")
        m = token.nextToken().toInt()
        n = token.nextToken().toInt()
        k = token.nextToken().toInt()

        board = Array(m) { IntArray(n) { 0 } }
        visit = Array(m) { BooleanArray(n) }
        rect = Array(k) { IntArray(4) }
        for (i in 0 until k) {
            token = StringTokenizer(readLine(), " ")
            for (j in 0 until 4)
                rect[i][j] = token.nextToken().toInt()
        }
    }

    fun fill() {
        for (r in rect) {
            val xLen = r[2] - r[0]
            val yLen = r[3] - r[1]
            for (i in 0 until xLen)
                for (j in 0 until yLen)
                    board[r[1] + j][r[0] + i] = 1
        }
    }

    fun dfs(x: Int, y: Int) {
        visit[x][y] = true
        area++

        for (i in 0 until 4) {
            val nx = x + dx[i]
            val ny = y + dy[i]
            if (nx < 0 || ny < 0 || nx >= m || ny >= n || visit[nx][ny] || board[nx][ny] == 1)
                continue
            dfs(nx, ny)
        }
    }

    fun dfsFun() {
        for (i in 0 until m) {
            for (j in 0 until n) {
                if (visit[i][j] || board[i][j] == 1)
                    continue
                area = 0
                dfs(i, j)
                result.add(area)
            }
        }
    }

    fun printResult() {
        val bw = BufferedWriter(OutputStreamWriter(System.out))

        result.sort()
        bw.write("${result.size}\n")
        for (r in result)
            bw.write("$r ")
        bw.write("\n")

        bw.flush()
        bw.close()
    }

    getInput()
    fill()
    dfsFun()
    printResult()
}
