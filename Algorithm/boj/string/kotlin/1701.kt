import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val line = readLine()
    val len = line.length

    var result = 0
    for (i in 0 until len) {
        result = max(kmp(line.substring(i)), result)
    }
    println(result)
}

private fun kmp(pattern: String): Int {
    val len = pattern.length
    val table = IntArray(len)
    var pidx = 0
    var maxPatternLength = 0
    for (idx in 1 until len) {
        while (pidx > 0 && pattern[idx] != pattern[pidx])
            pidx = table[pidx - 1]

        if (pattern[idx] == pattern[pidx]) {
            table[idx] = ++pidx
            maxPatternLength = max(maxPatternLength, table[idx])
        }
    }
    return maxPatternLength
}
