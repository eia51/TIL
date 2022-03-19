import java.util.*

private lateinit var solution: IntArray
private lateinit var visit: BooleanArray
private lateinit var arr: IntArray
private val map = HashMap<String, Boolean>()
private val sb = StringBuilder()
private var n = 0
private var m = 0

// https://www.acmicpc.net/problem/15664
fun main() {
    getInput()
    permuration(0)
    println(sb.toString())
}

private fun getInput() {
    val sc = Scanner(System.`in`)
    n = sc.nextInt()
    m = sc.nextInt()
    arr = IntArray(n)
    visit = BooleanArray(n)
    solution = IntArray(m)
    for (i in 0 until n)
        arr[i] = sc.nextInt()
    arr.sort()
}

private fun permuration(k: Int) {
    if (k == m) {
        printResult()
        return
    }

    for (i in k until n) {
        if (!visit[i]) {
            if (k == 0 || i >= solution[k-1]) {
                visit[i] = true
                solution[k] = i
                permuration(k + 1)
                visit[i] = false
            }
        }
    }
}

private fun printResult() {
    val ssb = StringBuilder()
    for (i in solution)
        ssb.append(arr[i]).append(" ")
    val tmp = ssb.toString()
    if (!map.containsKey(tmp)) {
        sb.append(tmp).append("\n")
        map[tmp] = true
    }
}
