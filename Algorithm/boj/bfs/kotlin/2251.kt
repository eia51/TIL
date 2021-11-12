import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

val queue: Queue<Pair<Int, Int>> = LinkedList()
lateinit var visited: Array<BooleanArray>

// ref -> https://www.acmicpc.net/problem/2251
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val tn = StringTokenizer(readLine(), " ")
    val a = tn.nextToken().toInt()
    val b = tn.nextToken().toInt()
    val c = tn.nextToken().toInt()

    visited = Array(a + 1) { BooleanArray(b + 1) { false } }

    visited[0][0] = true
    queue.add(Pair(0, 0))

    val result = bfs(a, b, c)
    result.sort()

    result.forEach { print("$it ") }
    println()
}

fun bfs(a: Int, b: Int, c: Int): ArrayList<Int> {
    val result = ArrayList<Int>()

    while (!queue.isEmpty()) {
        val p = queue.poll()
        val x = p.first
        val y = p.second
        val z = c - x - y

        if (x == 0) result.add(z)

        // x - y
        var water = min(x, b - y)
        pour(x - water, y + water)

        // x - z
        water = min(x, c - z)
        pour(x - water, y)

        // y - x
        water = min(y, a - x)
        pour(x + water, y - water)

        // y - z
        water = min(y, c - z)
        pour(x, y - water)

        // z- x
        water = min(z, a - x)
        pour(x + water, y)

        // z - y
        water = min(z, b - y)
        pour(x, y + water)
    }
    return result
}

fun pour(a: Int, b: Int) {
    if (!visited[a][b]) {
        visited[a][b] = true
        queue.add(Pair(a, b))
    }
}
