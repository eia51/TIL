import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val src = readLine()
    val pattern = readLine()
    kmpFunc(src, pattern)
}

private fun kmpFunc(src: String, pattern: String) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val table = getKmpTable(pattern)
    val sb = StringBuilder()
    var ans = 0
    var pidx = 0

    for (idx in src.indices) {
        while (pidx > 0 && src[idx] != pattern[pidx])
            pidx = table[pidx - 1]

        if (src[idx] == pattern[pidx]) {
            if (pidx == pattern.length - 1) {
                sb.append("${idx - pattern.length + 2} ")
                pidx = table[pidx]
                ans++
            } else pidx++
        }
    }
    bw.write("$ans\n$sb\n")
    bw.flush()
}

private fun getKmpTable(pattern: String): IntArray {
    val pl = pattern.length
    val table = IntArray(pl) { 0 }

    var pidx = 0
    for (idx in 1 until pl) {
        while (pidx > 0 && pattern[idx] != pattern[pidx])
            pidx = table[pidx - 1]

        if (pattern[idx] == pattern[pidx]) {
            table[idx] = ++pidx
        }
    }
    return table
}
