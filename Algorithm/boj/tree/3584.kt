import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

// https://www.acmicpc.net/problem/3584
fun main() {
    P3584().solve()
}

class P3584 {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val bi = BufferedReader(InputStreamReader(System.`in`))

    lateinit var parent: IntArray
    var q = IntArray(2)
    var t = 0
    var n = 0

    fun solve() {
        t = bi.readLine().toInt()

        repeat(t) {
            getInput()
            proc()
        }

        bw.flush()
        bw.close()
        bi.close()
    }

    fun getInput() {
        n = bi.readLine().toInt()
        parent = IntArray(n + 1) { it }

        for (i in 0 until n - 1) {
            val token = StringTokenizer(bi.readLine(), " ")
            val p = token.nextToken().toInt()
            val c = token.nextToken().toInt()
            parent[c] = p
        }
        val token = StringTokenizer(bi.readLine(), " ")
        q[0] = token.nextToken().toInt()
        q[1] = token.nextToken().toInt()
    }

    fun proc() {
        val parentMap = HashMap<Int, Boolean>()
        var cycle = q[0]
        while (true) {
            parentMap[cycle] = true
            if (cycle == parent[cycle])
                break
            cycle = parent[cycle]
        }

        cycle = q[1]
        while (true) {
            if (parentMap.containsKey(cycle)) {
                bw.write("$cycle\n")
                return
            }
            cycle = parent[cycle]
        }
    }
}
