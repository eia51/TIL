import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    P1068().solve()
}

class P1068 {
    lateinit var tree: Array<ArrayList<Int>>
    lateinit var leaf: IntArray
    lateinit var parent: IntArray
    lateinit var visit: BooleanArray
    var root = 0
    var n = 0
    var k = 0

    fun solve() {
        getInput()

        // 각 노드 별 리프 개수, 부모 노드정보를 기록한다
        leafCountDfs(root, -1)

        // 조건에 맞는 결과를 출력한다
        printResult()
    }

    fun getInput() = with(BufferedReader(InputStreamReader(System.`in`)))  {
        n = readLine().toInt()
        val token = StringTokenizer(readLine(), " ")
        tree = Array(n) { ArrayList() }
        visit = BooleanArray(n)
        leaf = IntArray(n)
        parent = IntArray(n)

        for (i in 0 until n) {
            val p = token.nextToken().toInt()
            if (p == -1)
                root = i
            else
                tree[p].add(i)
        }
        k = readLine().toInt()
    }

    fun leafCountDfs(curr: Int, prev: Int): Int {
        visit[curr] = true
        parent[curr] = prev

        if (tree[curr].size == 0) {
            leaf[curr] = 1
            return 1
        }

        var leafCnt = 0
        for (next in tree[curr]) {
            if (!visit[next])
                leafCnt += leafCountDfs(next, curr)
        }
        leaf[curr] = leafCnt
        return leafCnt
    }

    fun printResult() {
        // 루트를 삭제하는 경우는 리프 = 0
        if (k == root)
            println(0)
        // ★ 삭제하려는 노드(k)의 부모의 자식 수가 1인 경우,
        //    k가 삭제되어도 부모가 새로운 리프가 되어 리프 개수는 그대로 유지 됨
        else if (tree[parent[k]].size == 1)
            println(leaf[root])
        // 그 외 경우라면, root의 리프 수에서 삭제하려는 노드의 리프 수를 뺀 값이 정답
        else
            println(leaf[root] - leaf[k])
    }
}
