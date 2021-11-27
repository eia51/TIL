import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val visit = HashMap<Int, Boolean>()
    val inp = readLine().split(" ").map { it.toInt() }

    for (i in 0 until inp[1]) {
        val goal = readLine().toInt()
        var can = true
        var tmp = goal
        var firstMeet = 0
        while (tmp > 1) {
            if (visit.containsKey(tmp)) {
                firstMeet = tmp
                can = false
            }
            tmp /= 2
        }
        if (can) {
            visit[goal] = true
            bw.write("0\n")
        } else {
            bw.write("$firstMeet\n")
        }
    }
    bw.flush()
    bw.close()
}
