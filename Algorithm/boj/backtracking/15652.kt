import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    lateinit var ans: IntArray
    val sb = StringBuilder()

    fun recFunc(n: Int, m: Int, k: Int) {
        if (k == m) {
            for (i in 0 until m)
                sb.append("${ans[i]} ")
            sb.append("\n")
            return
        }

        val start = if (k == 0) 1 else ans[k-1]
        for (cand in start..n) {
            ans[k] = cand
            recFunc(n, m, k+1)
        }
    }

    val st = StringTokenizer(readLine(), " ")
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()
    ans = IntArray(m)

    recFunc(n, m, 0)
    println(sb.toString())
}
