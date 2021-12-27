import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private val bw = BufferedWriter(OutputStreamWriter(System.out))
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val str = readLine()
    kmp(str)
    bw.flush()
    bw.close()
}

private fun kmp(pattern: String) {
    val len = pattern.length
    val pi = IntArray(len)
    var pidx = 0
    for (idx in 1 until len) {
        while (pidx > 0 && pattern[idx] != pattern[pidx])
            pidx = pi[pidx - 1]
        if (pattern[idx] == pattern[pidx])
            pi[idx] = ++pidx
    }

    for (i in 1 until len) {
        val suf = pi[i]
        val pre = (i + 1) - pi[i]
        if (suf % pre == 0 && suf / pre > 0)
            bw.write("${i + 1} ${(suf / pre) + 1}\n")
    }
}
