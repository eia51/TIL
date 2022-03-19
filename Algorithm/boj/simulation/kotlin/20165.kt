import java.util.*

private lateinit var board: Array<IntArray>
private lateinit var down: Array<BooleanArray>
private var score = 0
private var n = 0
private var m = 0
private var r = 0

fun main() {
    input()
    start()
    printResult()
}

private fun input() {
    n = nextInt()
    m = nextInt()
    r = nextInt()

    board = Array(n) { IntArray(m) { nextInt() } }
    down = Array(n) { BooleanArray(m) { false } }
}

private fun start() {
    while (r-- > 0) {
        attack()
        defense()
    }
}

private fun attack() {
    // 공격 내용 입력 받기
    val y = nextInt() - 1
    val x = nextInt() - 1
    val dir = nextChar()
    var dy = 0
    var dx = 0
    when (dir) {
        'E' -> {
            dy = 0; dx = 1
        }
        'W' -> {
            dy = 0; dx = -1
        }
        'S' -> {
            dy = 1; dx = 0
        }
        else -> {
            dy = -1; dx = 0
        }
    }

    // 최초 입력지점에 대한 공격
    if (down[y][x]) return
    var attackPoint = board[y][x] - 1
    score++
    down[y][x] = true

    // 도미노 영향을 받는 지점에 대한 공격
    var ny = y
    var nx = x
    while (attackPoint-- > 0) {
        ny += dy
        nx += dx
        if (ny < 0 || nx < 0 || ny >= n || nx >= m) break

        if (down[ny][nx]) continue
        down[ny][nx] = true
        score++
        if (attackPoint < board[ny][nx] - 1)
            attackPoint = board[ny][nx] - 1
    }
}

private fun defense() {
    val y = nextInt() - 1
    val x = nextInt() - 1
    down[y][x] = false
}

private fun printResult() {
    bw.write("$score\n")
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (down[i][j]) bw.write("F ")
            else bw.write("S ")
        }
        bw.write("\n")
    }
    flush()
}

private val br = System.`in`.bufferedReader()
private val bw = System.out.bufferedWriter()
private var st = StringTokenizer("")

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}

private fun nextChar(): Char {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken()[0]
}
