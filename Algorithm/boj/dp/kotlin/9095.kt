import java.io.BufferedReader
import java.io.InputStreamReader

val map = HashMap<Int, Int>()

// ref -> https://www.acmicpc.net/problem/9095
fun getNumberOfCase(num: Int): Int {
    if (map.contains(num))
        return map[num] ?: 0

    return if (num <= 2)
        num
    else if (num == 3)
        4
    else
        getNumberOfCase(num - 1) + getNumberOfCase(num - 2) + getNumberOfCase(num - 3)
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val N = readLine().toInt()
    val numbers = IntArray(N)
    for (i in 0 until N)
        numbers[i] = readLine().toInt()

    for (num in numbers) {
        println(getNumberOfCase(num))
    }
}
