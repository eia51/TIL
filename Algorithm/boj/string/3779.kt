import java.io.BufferedReader
import java.io.InputStreamReader

private val resultSb = StringBuilder()
private var cnt = 1

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    while (true) {
        val n = readLine().toInt()
        if (n == 0) break
        val line = readLine()
        solve(n, line)
        cnt++
    }
    println(resultSb.trim())
}

private fun solve(n: Int, line: String) {
    resultSb.append("Test case #$cnt\n")
    val pi = getPi(line)

    for (i in pi.indices) {
        if (pi[i] == 0) continue
        val gcd = gcd(pi[i], i + 1)
        if (pi[i] + gcd == i + 1) {
            resultSb.append("${i + 1} ${(i + 1) / gcd}\n")
        }
    }
    resultSb.append("\n")
}

private fun getPi(pat: String): IntArray {
    val len = pat.length
    val pi = IntArray(len)
    var pidx = 0

    for (idx in 1 until len) {
        while (pidx > 0 && pat[idx] != pat[pidx])
            pidx = pi[pidx - 1]
        if (pat[idx] == pat[pidx])
            pi[idx] = ++pidx
    }
    return pi
}

private fun gcd(v1: Int, v2: Int): Int {
    val mod = v1 % v2
    if (mod == 0) return v2
    return gcd(v2, mod)
}
