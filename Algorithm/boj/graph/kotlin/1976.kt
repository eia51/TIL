import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val m = readLine().toInt()

    val expect = HashMap<Int, Boolean>()
    val expectSet = HashSet<Int>()
    val arr = Array(n) { IntArray(n) }
    val visit = BooleanArray(n)
    for (i in 0 until n) {
        val token = StringTokenizer(readLine(), " ")
        for (j in 0 until n) {
            arr[i][j] = token.nextToken().toInt()
        }
    }

    val token = StringTokenizer(readLine(), " ")
    var start = 0
    for (i in 0 until m) {
        val ip = token.nextToken().toInt()
        if (i == 0) start = ip
        expect[ip] = false
        expectSet.add(ip)
    }

    var expectCnt = 1
    val dq = ArrayDeque<Int>()
    dq.addLast(start - 1)
    visit[start - 1] = true
    expect[start] = true

    if (expectCnt == expectSet.size) {
        println("YES")
        return
    }

    while (!dq.isEmpty()) {
        val it = dq.poll()
        for (i in 0 until arr[it].size) {
            if (arr[it][i] == 1 && !visit[i]) {
                dq.addLast(i)
                visit[i] = true
                if (expect.containsKey(i + 1)) {
                    expect[i + 1] = true
                    expectCnt++
                    if (expectCnt == expectSet.size) {
                        println("YES")
                        return
                    }
                }
            }
        }
    }
    println("NO")
}
