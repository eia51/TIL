import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*


// ref -> https://www.acmicpc.net/problem/1766
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    var token = StringTokenizer(readLine(), " ")
    val n = token.nextToken().toInt()
    val m = token.nextToken().toInt()
    val graph = Array(n + 1) { PriorityQueue<Int>() }
    val degree = IntArray(n + 1) { 0 }

    for (i in 0 until m) {
        token = StringTokenizer(readLine(), " ")
        val parent = token.nextToken().toInt()
        val child = token.nextToken().toInt()
        graph[parent].add(child)
        degree[child] += 1
    }

    if (n == 1) {
        println(1)
        return
    }

    val q = PriorityQueue<Int>()
    for (i in 1..n) {
        if (degree[i] != 0) continue

        if (graph[i].size == 0) {
            bw.write("$i ")
        } else {
            q.add(i)
            while (q.size != 0) {
                val it = q.remove()
                bw.write("$it ")

                for (j in graph[it]) {
                    degree[j] -= 1
                    if (degree[j] != 0)
                        continue

                    q.add(j)
                    degree[j] = -1
                }
            }
        }
    }
    bw.flush()
}
