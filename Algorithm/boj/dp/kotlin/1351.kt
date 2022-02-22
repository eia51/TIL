import java.util.*

private val data = HashMap<Long, Long>()
private var n = 0L
private var p = 0L
private var q = 0L

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()){
    var st = StringTokenizer("")

    fun nextLong(): Long {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toLong()
    }

    n = nextLong()
    p = nextLong()
    q = nextLong()
}

private fun solve() {
    data[0] = 1
    println(dfs(n))
}

fun dfs(i: Long): Long {
    if (data.containsKey(i))
        return data[i]!!

    data[i] = dfs(i.div(p)) + dfs(i.div(q))
    return data[i]!!
}
