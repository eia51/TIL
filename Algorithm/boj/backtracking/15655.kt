import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine(), " ")
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val sb = StringBuilder()
    val solution = IntArray(m)
    val arr = IntArray(n)
    val visit = BooleanArray(n)
    st = StringTokenizer(readLine(), " ")
    var i = 0
    while (st.hasMoreTokens())
        arr[i++] = st.nextToken().toInt()
    arr.sort()

    fun recFunc(k: Int) {
        if (k == m) {
            for (i in 0 until m)
                sb.append(arr[solution[i]]).append(" ")
            sb.append("\n")
            return
        }
        var prev = k - 1
        if (prev < 0) prev = 0
        for (i in k until n) {
            if (solution[prev] > i || visit[i]) continue
            visit[i] = true
            solution[k] = i
            recFunc(k + 1)
            visit[i] = false
        }
    }

    recFunc(0)
    println(sb.toString())
}
