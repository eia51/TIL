import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    P3055().solve()
}

class P3055 {
    data class Point(val x: Int, val y: Int, val d: Int)

    lateinit var field: Array<CharArray>
    lateinit var limits: Array<IntArray>

    val dx = intArrayOf(-1, 1, 0, 0)
    val dy = intArrayOf(0, 0, -1, 1)

    lateinit var hog: Point
    val waters = ArrayList<Point>()
    var n = 0
    var m = 0

    fun solve() {
        getInput()
        waterBfs()
        hogBfs()
    }

    fun getInput() = with(BufferedReader(InputStreamReader(System.`in`))) {
        val token = StringTokenizer(readLine(), " ")
        n = token.nextToken().toInt()
        m = token.nextToken().toInt()
        field = Array(n) { CharArray(m) }

        for (i in 0 until n) {
            val line = readLine()
            var j = 0
            for (ch in line) {
                field[i][j] = ch
                if (ch == '*')
                    waters.add(Point(i, j, 0))
                else if (ch == 'S')
                    hog = Point(i, j, 0)
                j++
            }
        }
    }

    fun waterBfs() {
        limits = Array(n) { IntArray(m) }
        val visit = Array(n) { BooleanArray(m) }

        val dq = ArrayDeque<Point>()

        for (water in waters) {
            dq.addLast(Point(water.x, water.y, 0))
            visit[water.x][water.y] = true
        }

        while (!dq.isEmpty()) {
            val item = dq.poll()

            for (i in 0 until 4) {
                val nx = item.x + dx[i]
                val ny = item.y + dy[i]

                if (nx >= 0 && ny >= 0 && nx < n && ny < m && !visit[nx][ny] && field[nx][ny] == '.') {
                    dq.addLast(Point(nx, ny, item.d + 1))
                    visit[nx][ny] = true
                    limits[nx][ny] = item.d + 1
                }
            }
        }
    }

    fun hogBfs() {
        val visit = Array(n) { BooleanArray(m) }
        val dq = ArrayDeque<Point>()
        dq.add(hog)
        visit[hog.x][hog.y] = true

        while (!dq.isEmpty()) {
            val item = dq.poll()

            for (i in 0 until 4) {
                val nx = item.x + dx[i]
                val ny = item.y + dy[i]

                if (nx < 0 || ny < 0 || nx >= n || ny >= m || visit[nx][ny] || field[nx][ny] == 'X')
                    continue

                if (field[nx][ny] == 'D') {
                    println(item.d + 1)
                    return
                }

                if (available(nx, ny, item.d + 1) && field[nx][ny] == '.') {
                    dq.addLast(Point(nx, ny, item.d + 1))
                    visit[nx][ny] = true
                }
            }
        }

        println("KAKTUS")
    }

    fun available(nx: Int, ny: Int, d: Int): Boolean {
        if (limits[nx][ny] == 0)
            return true

        return limits[nx][ny] > d
    }
}
