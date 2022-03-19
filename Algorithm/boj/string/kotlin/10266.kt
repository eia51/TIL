import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private var n = 0
private const val lim = 360000
private val src = IntArray(lim * 2)
private val pat = IntArray(lim)

fun main() {
    getInput()
    kmp()
    flush()
}

private fun getInput() {
    n = nextInt()
    for (i in 1..n) {
        val num = nextInt()
        src[num] = 1
        src[num + lim] = 1
    }
    for (i in 1..n) {
        val num = nextInt()
        pat[num] = 1
    }
}

private fun kmp() {
    val table = getKmpTable()
    var pidx = 0

    for (idx in src.indices) {
        while (pidx > 0 && src[idx] != pat[pidx])
            pidx = table[pidx - 1]
        if (src[idx] == pat[pidx]) {
            pidx++
            if (pidx == lim) {
                bw.write("possible")
                return
            }
        }
    }
    bw.write("impossible")
}

private fun getKmpTable(): IntArray {
    val table = IntArray(lim)
    var pidx = 0
    for (idx in 1 until lim) {
        while (pidx > 0 && pat[idx] != pat[pidx])
            pidx = table[pidx - 1]
        if (pat[idx] == pat[pidx])
            table[idx] = ++pidx
    }
    return table
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
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
