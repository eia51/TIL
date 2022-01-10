import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val n = nextInt()
    val pq = PriorityQueue<Int>(n + 1)
    for (i in 0 until n) {
        for (j in 0 until n) {
            val num = nextInt()
            if (pq.size < n) {
                pq.add(num)
            } else if (pq.peek() < num) {
                pq.poll()
                pq.add(num)
            }
        }
    }

    bw.write("${pq.poll()}\n")
    flush()
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
