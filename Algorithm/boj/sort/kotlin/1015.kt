import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


private data class Item(val num: Int, val idx: Int) : Comparable<Item> {
    override fun compareTo(other: Item): Int {
        return num - other.num
    }
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val res = IntArray(n)
    val arr = ArrayList<Item>()

    val st = StringTokenizer(readLine(), " ")
    var j = 0
    while (st.hasMoreTokens()) {
        arr.add(Item(st.nextToken().toInt(), j))
        j++
    }
    arr.sort()

    for (i in 0 until n) {
        res[arr[i].idx] = i
    }

    res.forEach { print("$it ") }
    println()
}

