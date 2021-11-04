import java.io.BufferedReader
import java.io.InputStreamReader

// ref -> https://www.acmicpc.net/problem/1920
fun binarySearch(arr: List<Int>, value: Int): Int {
    var start = 0
    var mid = 0
    var end = arr.size - 1

    while (start <= end) {
        mid = (start + end) / 2
        if (arr[mid] == value)
            return 1
        else if (arr[mid] > value) {
            end = mid - 1
        } else {
            start = mid + 1
        }
    }
    return 0
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val N = readLine().toInt()
    val arr: List<Int> = readLine().split(" ").map { it.toInt() }.sorted()

    val M = readLine().toInt()
    val ins: List<Int> = readLine().split(" ").map { it.toInt() }

    for (i in ins) {
        println(binarySearch(arr, i))
    }
}
