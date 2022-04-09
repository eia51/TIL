import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    val arr = IntArray(3)
    arr[0] = nextInt()
    arr[1] = nextInt()
    arr[2] = nextInt()

    arr.sort()

    println(arr[1])
}
