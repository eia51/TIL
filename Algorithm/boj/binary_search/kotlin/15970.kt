import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

private val map = HashMap<Int, ArrayList<Int>>()
private var n = 0

fun main() {
    getInput()
    calcDistance()
}

private fun getInput() = with(BufferedReader(InputStreamReader(System.`in`))) {
    n = readLine().toInt()
    repeat(n) {
        val st = StringTokenizer(readLine(), " ")
        val v = st.nextToken().toInt()
        val c = st.nextToken().toInt()

        if (!map.containsKey(c))
            map[c] = ArrayList()

        map[c]!!.add(v)
    }
}

private fun calcDistance() {
    var result = 0
    for (color in map.keys) {
        val list = map[color]!!
        list.sort()

        for (i in list.indices) {
            val lp = i - 1
            val rp = i + 1

            val lgap =  if (lp >= 0) list[i] - list[lp]
                        else Int.MAX_VALUE

            val rgap =  if (rp < list.size) list[rp] - list[i]
                        else Int.MAX_VALUE

            result += min(lgap, rgap)
        }
    }
    println(result)
}
