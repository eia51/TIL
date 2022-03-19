import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private val map = HashMap<Int, ArrayList<Int>>()

fun main() {
    getInput()
    solve()
    flush()
}

private fun getInput() {
    val n = nextInt()
    for (i in 1..n) {
        val d = nextInt()
        val rw = nextInt()

        if (map.containsKey(d)) {
            map[d]!!.add(rw)
        } else {
            map[d] = arrayListOf(rw)
        }
    }
}

private fun solve() {
    var result = 0
    val pq = PriorityQueue<Int>()
    val keys = map.keys.sorted()
    for (deadline in keys) {
        for (reward in map[deadline]!!) {
            if (pq.size < deadline) {
                pq.add(reward)
            } else {
                if (pq.peek() < reward) {
                    pq.poll()
                    pq.add(reward)
                }
            }
        }
    }
    while (!pq.isEmpty())
        result += pq.poll()
    bw.write("$result\n")
}

private val bw = BufferedWriter(OutputStreamWriter(System.out))
private val br = BufferedReader(InputStreamReader(System.`in`))
private var st = StringTokenizer("")

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")

    return st.nextToken().toInt()
}
