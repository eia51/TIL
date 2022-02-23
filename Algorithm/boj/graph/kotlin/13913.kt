private val dist = IntArray(100001)
private val dp = IntArray(100001)
private var n = 0
private var k = 0

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    val line = readLine().split(" ")
    n = line[0].toInt()
    k = line[1].toInt()
}

private fun solve() {
    val dq = ArrayDeque<Int>()
    dq.add(n)

    while (dq.isNotEmpty()) {
        val x = dq.removeFirst()
        if (x == k) {
            printResult(x)
            return
        }
        for (i in intArrayOf(x + 1, x - 1, x * 2)) {
            if (i in 0..100000 && dist[i] == 0) {
                dq.add(i)
                dist[i] = dist[x] + 1
                dp[i] = x
            }
        }
    }
}

private fun printResult(ans: Int) {
    val list = ArrayList<Int>()
    var tmp = ans
    for (i in 0..dist[ans]) {
        list.add(tmp)
        tmp = dp[tmp]
    }
    list.reverse()

    val sb = StringBuilder()
    sb.append(list.size - 1).append('\n')
    for (i in list)
        sb.append(i).append(' ')
    sb.append('\n')
    println(sb)
}
