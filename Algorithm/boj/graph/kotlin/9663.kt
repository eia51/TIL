import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.abs

var result = 0
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    dfs(n, 0, ArrayList())
    println(result)
}

fun dfs(n: Int, rowCount: Int, cds: ArrayList<Int>) {
    if (rowCount == n) {
        result++
        return
    }

    for (col in 0 until n) {
        if (isAvailable(col, rowCount, cds)) {
            cds.add(col)
            dfs(n, rowCount + 1, cds)
            cds.removeAt(cds.size - 1)
        }
    }
}

fun isAvailable(col: Int, rowCount: Int, cds: ArrayList<Int>): Boolean {
    for (idx in 0 until cds.size) {
        val cd = cds[idx]
        if (cd == col || abs(cd - col) == (rowCount - idx)) {
            return false
        }
    }
    return true
}
