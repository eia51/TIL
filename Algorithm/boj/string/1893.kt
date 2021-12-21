import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))

fun main() {
    val n = br.readLine().toInt()
    for (i in 1..n) solve()
    flush()
}

private fun solve() {
    val sb = StringBuilder()
    var cnt = 0

    val a = br.readLine()!!
    val w = br.readLine()!!
    val s = br.readLine()!!

    if (w.length > s.length) {
        printResult(sb, 0)
        return
    }

    if (containOnce(s, w)) {
        if (cnt > 0) sb.append(" ")
        sb.append("0")
        cnt++
    }

    val aIndexMap = HashMap<Char, Int>()
    for (i in a.indices) aIndexMap[a[i]] = i

    for (shift in 1 until a.length) {
        val shiftedPattern = getShiftString(a, aIndexMap, w, shift)
        if (containOnce(s, shiftedPattern)) {
            if (cnt > 0) sb.append(" ")
            sb.append("$shift")
            cnt++
        }
    }
    printResult(sb, cnt)
}

private fun getShiftString(a: String, aIndexMap: HashMap<Char, Int>, w: String, shift: Int): String {
    val sb = StringBuilder()
    for (ch in w) {
        val idx = (aIndexMap[ch]!! + shift) % a.length
        sb.append(a[idx])
    }
    return sb.toString()
}

private fun containOnce(src: String, pattern: String): Boolean {
    fun getKmpTable(): IntArray {
        val len = pattern.length
        val table = IntArray(len) { 0 }

        var pidx = 0
        for (idx in 1 until len) {
            while (pidx > 0 && pattern[idx] != pattern[pidx])
                pidx = table[pidx - 1]
            if (pattern[idx] == pattern[pidx])
                table[idx] = ++pidx
        }
        return table
    }

    val table = getKmpTable()
    var findCount = 0
    var pidx = 0
    for (idx in src.indices) {
        while (pidx > 0 && src[idx] != pattern[pidx])
            pidx = table[pidx - 1]
        if (src[idx] == pattern[pidx]) {
            pidx++
            if (pidx == pattern.length) {
                findCount++
                pidx = table[pidx - 1]
                if (findCount > 1) return false
            }
        }
    }
    return findCount == 1
}

private fun printResult(sb: StringBuilder, cnt: Int) {
    when (cnt) {
        0 -> bw.write("no solution\n")
        1 -> bw.write("unique: $sb\n")
        else -> bw.write("ambiguous: $sb\n")
    }
}

private fun flush() {
    br.close()
    bw.flush()
    bw.close()
}
