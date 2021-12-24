import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private var st = StringTokenizer("")

private var n = 0
private var k = 0

private lateinit var programs: Array<IntArray>

fun main() {
    getInput()
    if (hasCommonPattern()) {
        bw.write("YES")
    } else {
        bw.write("NO")
    }
    flush()
}

private fun getInput() {
    n = nextInt()
    k = nextInt()

    programs = Array(n) { IntArray(0) }
    for (i in 0 until n) {
        val m = nextInt()
        programs[i] = IntArray(m) { nextInt() }
    }
}

private fun hasCommonPattern(): Boolean {
    val base = programs[0]
    for (i in 0..base.size - k) {
        var j = 0
        val pattern = IntArray(k) { base[i + (j++)] }

        var contains = true
        for (k in 1 until n) {
            if (!programHasPattern(programs[k], pattern)) {
                contains = false
                break
            }
        }
        if (contains) return true
    }
    return false
}

private fun programHasPattern(src: IntArray, pattern: IntArray): Boolean {
    val plen = pattern.size
    fun getKmpTable(): IntArray {
        val pi = IntArray(plen)
        var pidx = 0
        for (idx in 1 until plen) {
            while (pidx > 0 && pattern[idx] != pattern[pidx])
                pidx = pi[pidx - 1]
            if (pattern[idx] == pattern[pidx])
                pi[idx] = ++pidx
        }
        return pi
    }

    val pi = getKmpTable()
    var pidx = 0
    for (idx in src.indices) {
        while (pidx > 0 && src[idx] != pattern[pidx])
            pidx = pi[pidx - 1]
        if (src[idx] == pattern[pidx]) {
            pidx++
            if (pidx == plen) {
                return true
            }
        }
    }
    pidx = 0
    for (idx in src.size - 1 downTo 0) {
        while (pidx > 0 && src[idx] != pattern[pidx])
            pidx = pi[pidx - 1]
        if (src[idx] == pattern[pidx]) {
            pidx++
            if (pidx == plen) {
                return true
            }
        }
    }
    return false
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")

    return st.nextToken().toInt()
}

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

