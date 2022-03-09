import java.util.*

private data class Item(val dist: Int, val time: Int)

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
    val caseMap = HashMap<Int, Int>()
    var minTime = Int.MAX_VALUE

    val visit = HashMap<Int, Boolean>()
    val dq = ArrayDeque<Item>()
    dq.add(Item(n, 0))

    while (dq.isNotEmpty()) {
        val item = dq.poll()
        visit[item.dist] = true

        if (minTime < item.time)
            continue

        if (item.dist == k) {
            minTime = kotlin.math.min(minTime, item.time)
            if (!caseMap.containsKey(item.time)) caseMap[item.time] = 1
            else caseMap[item.time] = caseMap[item.time]!! + 1
            continue
        }

        var curr = item.dist * 2
        if (curr in 0 .. 100000 && !visit.containsKey(curr))
            dq.add(Item(curr, item.time + 1))
        curr = item.dist + 1
        if (curr in 0 .. 100000 && !visit.containsKey(curr))
            dq.add(Item(curr, item.time +  1))
        curr = item.dist - 1
        if (curr in 0 .. 100000 && !visit.containsKey(curr))
            dq.add(Item(curr, item.time + 1))
    }

    val answer = StringBuilder()
        .append(minTime)
        .append("\n")
        .append(caseMap[minTime])
        .toString()
    println(answer)
}
