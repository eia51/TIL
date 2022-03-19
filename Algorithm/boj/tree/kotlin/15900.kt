import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    data class Node(val v: Int, val level: Int)

    lateinit var tree: Array<ArrayList<Int>>
    lateinit var level: IntArray
    var n = 0
    var sumOfLevel = 0

    fun getInput() {
        n = readLine().toInt()
        tree = Array(n + 1) { ArrayList() }
        level = IntArray(n + 1)

        repeat(n - 1) {
            val token = StringTokenizer(readLine(), " ")
            val v1 = token.nextToken().toInt()
            val v2 = token.nextToken().toInt()

            tree[v1].add(v2)
            tree[v2].add(v1)
        }
    }

    fun bfsDepthAndParent() {
        val dq = ArrayDeque<Node>()
        val visit = BooleanArray(n + 1)

        dq.add(Node(1, 0))
        visit[1] = true

        while (!dq.isEmpty()) {
            val it = dq.poll()
            level[it.v] = it.level

            var hasChild = false
            for (child in tree[it.v]) {
                if (!visit[child]) {
                    dq.add(Node(child, it.level + 1))
                    visit[child] = true
                    hasChild = true
                }
            }

            if (!hasChild)
                sumOfLevel += it.level
        }
    }

    fun solve() {
        if (sumOfLevel % 2 == 0)
            println("No")
        else
            println("Yes")
    }

    getInput()
    bfsDepthAndParent()
    solve()
}
