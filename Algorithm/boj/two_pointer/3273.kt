import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val st = StringTokenizer(readLine(), " ")
    val arr = IntArray(n) { st.nextToken().toInt() }
    val k = readLine().toInt()
    arr.sort()

    var result = 0
    var left = 0
    var right = arr.size - 1
    while (left < right) {
        val tmp = arr[left] + arr[right]
        if (tmp == k) {
            result++
            left++
            right--
        } else if (tmp > k) {
            right--
        } else {
            left++
        }
    }

    println(result)
}
