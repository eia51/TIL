import java.io.BufferedReader
import java.io.InputStreamReader

// ref -> https://www.acmicpc.net/problem/1181
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val arr = Array<String>(n) { readLine() }
    arr.quickSort()

    val map = HashMap<String, Boolean>()
    arr.forEach {
        if (!map.containsKey(it)) {
            println(it)
            map[it] = true
        }
    }
}

fun Array<String>.quickSort(left: Int = 0, right: Int = this.size - 1) {
    val mid = partition(this, left, right)
    if (left < mid - 1)
        quickSort(left, mid - 1)
    if (mid < right)
        quickSort(mid, right)
}

fun partition(array: Array<String>, start: Int, end: Int): Int {
    var left = start
    var right = end
    val pivot = array[(left + right) / 2]

    while (left <= right) {
        while (pivot.myCompare(array[left]))
            left++

        while (array[right].myCompare(pivot))
            right--

        if (left <= right) {
            array[right] = array[left].also { array[left] = array[right] }
            left++
            right--
        }
    }
    return left
}

fun String.myCompare(target: String): Boolean {
    if (this.length > target.length)
        return true

    else if (this.length < target.length)
        return false

    for (i in this.indices)
        if (this[i] != target[i])
            return this[i] > target[i]

    return false
}


