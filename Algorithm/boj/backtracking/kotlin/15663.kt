import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

// https://www.acmicpc.net/problem/15663
fun main() {
    P15663().solve()
}

class P15663 {
    lateinit var solution: IntArray
    lateinit var visit: BooleanArray
    lateinit var arr: IntArray
    val map = HashMap<String, Boolean>()
    val sb = StringBuilder()
    var n = 0
    var m = 0

    fun solve() {
        getInput()
        recFunc(0)
        println(sb.toString())
    }

    fun getInput() = with(BufferedReader(InputStreamReader(System.`in`))) {
        var st = StringTokenizer(readLine(), " ")
        n = st.nextToken().toInt()
        m = st.nextToken().toInt()

        arr = IntArray(n)
        visit = BooleanArray(n)
        solution = IntArray(m)
        st = StringTokenizer(readLine(), " ")
        var i = 0
        while (st.hasMoreTokens())
            arr[i++] = st.nextToken().toInt()
        arr.sort()
    }

    fun recFunc(k: Int) {
        val ssb = StringBuilder()
        if (k == m) {
            for (i in solution) ssb.append(arr[i]).append(" ")
            val tmp = ssb.toString()
            if (!map.containsKey(tmp)) {
                sb.append(tmp).append("\n")
                map[tmp] = true
            }
            return
        }

        for (cand in 0 until n) {
            if (!visit[cand]) {
                visit[cand] = true
                solution[k] = cand
                recFunc(k + 1)
                visit[cand] = false
            }
        }
    }
}
