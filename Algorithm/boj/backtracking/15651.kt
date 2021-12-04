import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    lateinit var ans: IntArray
    val sb = StringBuilder()

    fun recFunc(k: Int, n: Int, m: Int) {
        if (k == m + 1) {
            for (i in 1..m) sb.append("${ans[i]} ")
            sb.append("\n")
            return
        }
        for (i in 1..n) {
            ans[k] = i
            recFunc(k + 1, n, m)
        }
    }

    val st = StringTokenizer(readLine(), " ")
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()
    ans = IntArray(m + 1)

    recFunc(1, n, m)
    println(sb.toString())
}
