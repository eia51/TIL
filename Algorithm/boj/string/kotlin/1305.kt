import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val line = readLine()

    val pi = IntArray(n)
    var pidx = 0
    for (idx in 1 until n) {
        while (pidx > 0 && line[idx] != line[pidx])
            pidx = pi[pidx - 1]
        if (line[idx] == line[pidx])
            pi[idx] = ++pidx
    }
    println(n - pi[n - 1])
}
