private lateinit var arr: Array<CharArray>
private val visit = BooleanArray(26)
private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, 1, -1)
private var R = 0
private var C = 0
private var ans = 0

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    val line1 = readLine().split(" ")
    R = line1[0].toInt()
    C = line1[1].toInt()
    arr = Array(R) { readLine().toCharArray() }
}

private fun solve() {
    val startX = 0
    val startY = 0
    visit[arr[startY][startX].code - 65] = true
    dfs(startY, startX, 1)
    println(ans)
}

private fun dfs(y: Int, x: Int, d: Int) {
    ans = kotlin.math.max(ans, d)

    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]
        if (nx in 0 until C && ny in 0 until R) {
            val code = arr[ny][nx].code - 65
            if (!visit[code]) {
                visit[code] = true
                dfs(ny, nx, d + 1)
                visit[code] = false
            }
        }
    }
}
