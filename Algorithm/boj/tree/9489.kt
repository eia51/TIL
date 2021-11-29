import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    P9489().solve()
}

class P9489 {
    data class Node(val v: Int, val level: Int)
    lateinit var tree: HashMap<Int, ArrayList<Int>>
    lateinit var levelMap: HashMap<Int, Int>
    lateinit var parent: HashMap<Int, Int>
    lateinit var inputs: ArrayList<Int>

    var root = 0
    var currParent = 0
    var n = -1
    var k = -1

    fun solve() = with(BufferedReader(InputStreamReader(System.`in`))) {
        val bw = BufferedWriter(OutputStreamWriter(System.out))
        while (true) {
            var token = StringTokenizer(readLine(), " ")
            n = token.nextToken().toInt()
            k = token.nextToken().toInt()
            if (n == 0 && k == 0)
                break

            tree = HashMap<Int, ArrayList<Int>>()
            levelMap = HashMap<Int, Int>()
            parent = HashMap()
            inputs = ArrayList()
            root = 0
            currParent = 0

            token = StringTokenizer(readLine(), " ")
            var prev = 0
            while (token.hasMoreTokens()) {
                val v = token.nextToken().toInt()
                inputs.add(v)
                makeTree(v, prev)
                prev = v
            }

            bfs(root)
            var result = 0
            for (i in inputs) {
                if (levelMap[i] == levelMap[k] && parent[i] != parent[k])
                    result ++
            }
            bw.write("$result\n")
        }
        bw.flush()
        bw.close()
    }

    fun makeTree(node: Int, prev: Int) {
        if (prev == 0) {
            root = node
            tree[currParent] = ArrayList()
            return
        }

        if (node != prev + 1)
            syncNextParent()

        tree[currParent]!!.add(node)
        parent[node] = currParent
    }

    fun syncNextParent() {
        val myIndex = inputs.indexOf(currParent)
        currParent = inputs[myIndex + 1]

        if (!tree.containsKey(currParent))
            tree[currParent] = ArrayList()
    }

    fun bfs(root: Int) {
        val dq = ArrayDeque<Node>()
        dq.addLast(Node(root, 1))

        while (!dq.isEmpty()) {
            val item = dq.poll()

            levelMap[item.v] = item.level

            // 필요 이상으로 탐색이 진행되는 경우 반복 탈출
            if (levelMap.containsKey(item.v) && levelMap[item.v]!! < item.level)
                break

            if (tree[item.v] == null)
                continue

            // 하위 노드들에 대해서 bfs 진행
            for (i in tree[item.v]!!)
                dq.addLast(Node(i, item.level + 1))
        }
    }
}
