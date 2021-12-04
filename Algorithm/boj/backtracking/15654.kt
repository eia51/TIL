import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    P15654().solve()
}

class P15654 {
    lateinit var ans: IntArray
    lateinit var arr: IntArray
    lateinit var select: BooleanArray
    val sb = StringBuilder()

    var n = 0
    var m = 0

    fun solve() {
        getInput()
        recFunc(0)
        println(sb.toString())
    }

    private fun getInput() = with(BufferedReader(InputStreamReader(System.`in`))) {
        var st = StringTokenizer(readLine(), " ")
        n = st.nextToken().toInt()
        m = st.nextToken().toInt()
        ans = IntArray(m)

        st = StringTokenizer(readLine(), " ")
        arr = IntArray(n)
        select = BooleanArray(n)

        var i = 0
        while (st.hasMoreTokens())
            arr[i++] = st.nextToken().toInt()
        arr.sort()
    }

    private fun recFunc(k: Int) {
        if (k == m) {
            for (i in 0 until m) {
                sb.append("${arr[ans[i]]} ")
            }
            sb.append("\n")
            return
        }

        for (i in 0 until n) {
            if (select[i]) continue
            ans[k] = i
            select[i] = true
            recFunc(k + 1)
            select[i] = false
        }
    }
}
