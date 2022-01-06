import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val n = nextInt()
    val m = nextInt()

    val a = IntArray(n) { nextInt() }
    val b = IntArray(m) { nextInt() }

    var pa = 0
    var pb = 0
    while (pa < n && pb < m) {
        if (a[pa] == b[pb]) bw.write("${a[pa++]} ${b[pb++]} ")
        else if (a[pa] > b[pb]) bw.write("${b[pb++]} ")
        else bw.write("${a[pa++]} ")
    }

    while (pa < n) bw.write("${a[pa++]} ")
    while (pb < m) bw.write("${b[pb++]} ")

    flush()
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}

private fun flush() {
    br.close()
    bw.flush()
    bw.close()
}
