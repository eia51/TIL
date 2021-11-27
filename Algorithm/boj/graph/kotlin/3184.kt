import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

// https://www.acmicpc.net/problem/3184
fun main() {
    P3184().solve()
}

class P3184 {
    lateinit var area: Array<CharArray>
    lateinit var visit: Array<BooleanArray>

    val dx = intArrayOf(0, 0, 1, -1)
    val dy = intArrayOf(1, -1, 0, 0)

    // [0] sheep, [1] wolf
    val animals = IntArray(2)
    val result = IntArray(2)

    fun solve() = with(BufferedWriter(OutputStreamWriter(System.out))) {
        getInput()
        checkRegion()
        write("${result[0]} ${result[1]}")
        flush()
    }

    private fun getInput() = with(BufferedReader(InputStreamReader(System.`in`))) {
        val rc = readLine().split(" ").map { it.toInt() }
        visit = Array(rc[0]) { BooleanArray(rc[1]) }
        area = Array(rc[0]) { readLine().toCharArray() }
    }

    private fun dfs(x: Int, y: Int) {
        visit[x][y] = true
        if (area[x][y] == 'o')
            animals[0]++
        else if (area[x][y] == 'v')
            animals[1]++

        for (i in dx.indices) {
            val nx = x + dx[i]
            val ny = y + dy[i]

            if (nx !in area.indices || ny !in area[0].indices || visit[nx][ny] || area[nx][ny] == '#')
                continue

            dfs(nx, ny)
        }
    }

    private fun checkRegion() {
        for (i in area.indices) {
            for (j in area[i].indices) {
                if (!visit[i][j] && area[i][j] != '#') {

                    animals.fill(0)

                    dfs(i, j)

                    if (animals[0] > animals[1])
                        result[0] += animals[0]
                    else
                        result[1] += animals[1]
                }
            }
        }
    }

}
