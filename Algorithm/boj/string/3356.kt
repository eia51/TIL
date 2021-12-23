import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val l = readLine().toInt()
    val line = readLine()

    val pi = IntArray(l)
    var pidx = 0
    for (idx in 1 until l) {
        while (pidx > 0 && line[idx] != line[pidx])
            pidx = pi[pidx - 1]
        if (line[idx] == line[pidx])
            pi[idx] = ++pidx
    }
    println(l - pi[l - 1])
}
