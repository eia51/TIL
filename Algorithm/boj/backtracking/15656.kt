import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

private var n = 0
private var m = 0
private val sb = StringBuilder()
private lateinit var solution: IntArray
private lateinit var arr: IntArray

fun main() {
    getInput()
    recFunc(0)
    println(sb.toString())
}

private fun recFunc(k: Int) {
    if (k == m) {
        for (i in solution) sb.append(arr[i]).append(" ")
        sb.append("\n")
        return
    }

    for (i in 0 until n) {
        solution[k] = i
        recFunc(k + 1)
    }
}

private fun getInput() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine(), " ")
    n = st.nextToken().toInt()
    m = st.nextToken().toInt()

    solution = IntArray(m)
    arr = IntArray(n)
    st = StringTokenizer(readLine(), " ")
    var i = 0
    while (st.hasMoreTokens())
        arr[i++] = st.nextToken().toInt()
    arr.sort()
}
