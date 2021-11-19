
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

private lateinit var arr: Array<IntArray>
private lateinit var visit: Array<BooleanArray>
private lateinit var grpMap: Array<IntArray>

private val dx = intArrayOf(0, 0, 1, -1)
private val dy = intArrayOf(1, -1, 0, 0)

private class Block(val x: Int, val y: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()

    arr = Array(n) { IntArray(m) }
    visit = Array(n) { BooleanArray(m) }
    grpMap = Array(n) { IntArray(m) { -1 } }

    val walls = ArrayList<Block>()
    val zeros = ArrayList<Block>()

    // 입력 (무시)
    for (i in 0 until n) {
        var j = 0
        val line = readLine()
        for (ch in line) {
            arr[i][j] = ch.code - 48
            if (arr[i][j] == 1)
                walls.add(Block(i, j))
            else
                zeros.add(Block(i, j))
            j++
        }
    }

    // 여기서부터 시작!
    
    // - 0이 입력 된 좌표를 순회한다
    // - 인접한 0을 그룹 단위로 묶는다
    // - 그룹에 대한 정보는 'grpMap[][]' 에 저장되어있다
    // - 묶은 그룹에 포함 된 블록 개수를 'lengthMap' 에 저장한다
    var grpId = 0
    val lengthMap = HashMap<Int, Int>()
    for (zero in zeros) {
        if (!visit[zero.x][zero.y]) {
            val len = bfs(zero, n, m, ++grpId) + 1
            lengthMap[grpId] = len
        }
    }

    // - 1(벽)이 입력 된 좌표를 순회한다
    // - 벽의 상하좌우에 위치한 그룹에 포함 된 개수를 더한다
    // - 중복해서 더해지지 않도록 주의한다.
    for (wall in walls) {
        val grpVisit = HashMap<Int, Boolean>()
        var count = 1
        for (i in 0 until 4) {
            val nx = wall.x + dx[i]
            val ny = wall.y + dy[i]

            if (nx < 0 || ny < 0 || nx >= n || ny >= m || arr[nx][ny] != 0)
                continue

            val id = grpMap[nx][ny]
            //그룹 사이즈가 중복해서 더해지지 않도록 검사
            if (!grpVisit.containsKey(id)) {
                count += lengthMap[id]!!
                grpVisit[id] = true
            }
        }
        arr[wall.x][wall.y] = count % 10
    }

    // 정답 출력 : StringBuilder 사용
    val bd = StringBuilder()
    arr.forEach { it ->
        it.forEach {
            bd.append("$it")
        }
        bd.append("\n")
    }
    println(bd.toString())
}

private fun bfs(zero: Block, n: Int, m: Int, grpId: Int): Int {
    val dq = ArrayDeque<Block>()

    dq.addLast(Block(zero.x, zero.y))
    visit[zero.x][zero.y] = true

    var canVisit = 0
    while (!dq.isEmpty()) {
        val block = dq.poll()
        grpMap[block.x][block.y] = grpId

        for (i in 0 until 4) {
            val nx = block.x + dx[i]
            val ny = block.y + dy[i]

            if (nx < 0 || ny < 0 || nx >= n || ny >= m || arr[nx][ny] != 0 || visit[nx][ny])
                continue

            dq.addLast(Block(nx, ny))
            visit[nx][ny] = true
            canVisit++
        }
    }

    return canVisit
}
