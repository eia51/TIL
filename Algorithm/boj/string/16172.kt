import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val src = readLine()
    val pattern = readLine()
    kmpFunc(src, pattern)
}

private fun kmpFunc(src: String, pattern: String) {
    val table = getKmpTable(pattern)
    var pidx = 0
    for (idx in src.indices) {
        when (src[idx].code) {
            in 48..57 -> continue
            else -> {
                while (pidx > 0 && src[idx] != pattern[pidx])
                    pidx = table[pidx - 1]
                if (src[idx] == pattern[pidx]) {
                    if (pidx == pattern.length - 1) {
                        println(1)
                        return
                    } else pidx++
                }
            }
        }
    }
    println(0)
}

private fun getKmpTable(pattern: String): IntArray {
    val len = pattern.length
    val table = IntArray(len) { 0 }

    var pidx = 0
    for (idx in 1 until len) {
        while (pidx > 0 && pattern[idx] != pattern[pidx])
            pidx = table[pidx - 1]
        if (pattern[idx] == pattern[pidx]) {
            table[idx] = ++pidx
        }
    }
    return table
}
