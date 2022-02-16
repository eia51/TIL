private var n = 0
private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, 1, -1)
private val rg = charArrayOf('R', 'G')
private lateinit var arr: Array<CharArray>
private lateinit var visitN: Array<BooleanArray>
private lateinit var visitAb: Array<BooleanArray>

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    n = readLine().toInt()
    arr = Array(n) { readLine().toCharArray() }
    visitN = Array(n) { BooleanArray(n) { false } }
    visitAb = Array(n) { BooleanArray(n) { false } }
}

private fun solve() {
    var normal = 0
    var abnormal = 0

    for (i in 0 until n) {
        for (j in 0 until n) {
            if (!visitN[i][j]) {
                dfsN(i, j, arr[i][j])
                normal++
            }

            if (!visitAb[i][j]) {
                dfsAb(i, j, arr[i][j])
                abnormal++
            }
        }
    }

    println("$normal $abnormal")
}

private fun dfsN(y: Int, x: Int, ch: Char) {
    visitN[y][x] = true
    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]
        if (nx in 0 until n && ny in 0 until n && !visitN[ny][nx] && ch == arr[ny][nx])
            dfsN(ny, nx, ch)
    }
}

private fun dfsAb(y: Int, x: Int, ch: Char) {
    visitAb[y][x] = true
    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]
        if (nx in 0 until n && ny in 0 until n && !visitAb[ny][nx]) {
            if (ch in rg && arr[ny][nx] in rg)
                dfsAb(ny, nx, ch)
            else if (ch == arr[ny][nx])
                dfsAb(ny, nx, ch)
        }
    }
}
