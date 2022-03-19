import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.util.*

private lateinit var parent: IntArray
private var treeCount = 0
private var round = 1
private var n = 0
private var m = 0
private val sb = StringBuilder()

fun main() {
    round = 1
    while (true) {
        n = nextInt()
        m = nextInt()
        treeCount = 0

        if (n == 0 && m == 0)
            break
        else if (m == 0)
            treeCount = n
        else
            treeCount = getTreeCount()

        printResult(round++, treeCount)
    }
    val res = sb.toString()
    bw.write("$sb")
    if (res == ans) bw.write("CORRECT\n")
    else bw.write("WRONG\n")
    flush()
}

private fun getTreeCount(): Int {
    parent = IntArray(n + 1) { it }

    for (i in 1..m) {
        val v1 = nextInt()
        val v2 = nextInt()
        union(v1, v2)
    }

    val hs = HashSet<Int>()
    for (i in 1..n) {
        val p = find(i)
        if (p > 0) hs.add(p)
    }
    return hs.size
}

private fun find(x: Int): Int {
    if (parent[x] == x) return x
    val p = find(parent[x])
    parent[x] = p
    return p
}

private fun union(x: Int, y: Int){
    val v1 = find(x)
    val v2 = find(y)

    if (v1 == v2) {
        parent[v2] = v1
        parent[v1] = 0
    } else if(v1 > v2) {
        parent[v1] = v2
    } else {
        parent[v2] = v1
    }
}

private fun printResult(round: Int, count: Int) {
    val msg = when (count) {
        0 -> "No trees."
        1 -> "There is one tree."
        else -> "A forest of $count trees."
    }
//    bw.write("Case $round: $msg\n")
    sb.append("Case $round: $msg\n")
}

private val bw = BufferedWriter(OutputStreamWriter(System.out))
//private val br = BufferedReader(InputStreamReader(System.`in`))
private val br = File("E:\\MyDown\\problems\\probH\\cases\\test1\\trees.in").bufferedReader()
private val ans = File("E:\\MyDown\\problems\\probH\\cases\\test1\\trees.out").readText()
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}

private fun flush() {
    br.close()
    bw.flush()
    bw.close()
}
