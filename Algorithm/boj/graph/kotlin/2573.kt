
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.max

data class Ice(val x: Int, val y: Int, var melt: Int)

private val dx = intArrayOf(0, 0, 1, -1)
private val dy = intArrayOf(1, -1, 0, 0)

lateinit var arr: Array<IntArray>
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()

    val ices = ArrayList<Ice>()
    var maxIceHeight = Int.MIN_VALUE

    arr = Array(n) { IntArray(m) }
    for (i in 0 until n) {
        token = StringTokenizer(readLine(), " ")
        for (j in 0 until m) {
            arr[i][j] = token.nextToken().toInt()
            if (arr[i][j] != 0) {
                ices.add(Ice(i, j, 0))
                maxIceHeight = max(maxIceHeight, arr[i][j])
            }
        }
    }

    var age = 0
    while (true) {
        // 빙하 덩어리가 분리되었는지 검사한다 (bfs)
        val visit = Array(n) { BooleanArray(m) }
        var landCount = 0
        for (ice in ices) {
            if (arr[ice.x][ice.y] > 0 && !visit[ice.x][ice.y]) {
                bfs(ice, visit, n, m)
                landCount++
            }
        }
        // 땅이 2개 이상이라면 결과를 출력한다
        if (landCount > 1) {
            println(age)
            return
        }

        // 땅이 1개 이하라면 녹일 빙하 값을 계산하고
        calculateMeltingHeight(ices, n, m)

        // 빙하를 녹인 뒤 다음 세대로 넘어간다
        if (!melting(ices)) {
            println(0)
            return
        }

        // 에이징
        age++
    }
}

fun calculateMeltingHeight(ices: ArrayList<Ice>, n: Int, m: Int) {
    for (ice in ices) {
        // 이미 녹은 빙하는 검사 할 필요가 없다
        if (arr[ice.x][ice.y] < 0)
            continue

        var melt = 0
        for (i in 0 until 4) {
            val nx = ice.x + dx[i]
            val ny = ice.y + dy[i]

            if (nx >= 0 && ny >= 0 && nx < n && ny < m && arr[nx][ny] <= 0)
                melt++
        }
        ice.melt = melt
    }
}

fun melting(ices: ArrayList<Ice>):Boolean {
    var isLeftIce = false
    for (ice in ices) {
        if (arr[ice.x][ice.y] > 0) {
            arr[ice.x][ice.y] -= ice.melt
            if (arr[ice.x][ice.y] > 0)
                isLeftIce = true
        }
    }
    return isLeftIce
}


fun bfs(ice: Ice, visit: Array<BooleanArray>, n: Int, m: Int) {
    val dq = ArrayDeque<Ice>()

    dq.addLast(ice)
    visit[ice.x][ice.y] = true

    while (!dq.isEmpty()) {
        val it = dq.poll()

        for (i in 0 until 4) {
            val nx = it.x + dx[i]
            val ny = it.y + dy[i]

            if (nx >= 0 && ny >= 0 && nx < n && ny < m && arr[nx][ny] > 0 && !visit[nx][ny]) {
                dq.addLast(Ice(nx, ny, 0))
                visit[nx][ny] = true
            }
        }
    }
}
