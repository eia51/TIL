data class Point(val x: Int, val y: Int, var d: Int)

private val dx = intArrayOf(-1, -2, -2, -1, 1, 2, 2, 1)
private val dy = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()
    val arr = Array(n) { IntArray(n) }

    token = StringTokenizer(readLine(), " ")
    val k = Point(token.nextToken().toInt() - 1, token.nextToken().toInt() - 1, 0)

    bfs(k, arr, n)

    for (i in 0 until m) {
        token = StringTokenizer(readLine(), " ")
        bw.write("${arr[token.nextToken().toInt() - 1][token.nextToken().toInt() - 1]} ")
    }

    bw.flush()
    bw.close()
}

private fun bfs(knight: Point, arr: Array<IntArray>, n: Int): Int {
    val dq = ArrayDeque<Point>()
    val visit = Array(n) { BooleanArray(n) }

    dq.addLast(knight)
    visit[knight.x][knight.y] = true

    while (!dq.isEmpty()) {
        val k = dq.poll()
        arr[k.x][k.y] = k.d

        for (i in 0 until 8) {
            val nx = k.x + dx[i]
            val ny = k.y + dy[i]

            if (nx < 0 || ny < 0 || nx >= n || ny >= n || visit[nx][ny])
                continue

            dq.addLast(Point(nx, ny, k.d + 1))
            visit[nx][ny] = true
        }
    }
    return -1
}
