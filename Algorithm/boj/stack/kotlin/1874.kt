import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Stack

// ref -> https://www.acmicpc.net/problem/1874
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val arr = IntArray(n) { 0 }
    var count = 1
    val stack = Stack<Int>()
    val builder = StringBuilder()

    for (i in arr.indices) {
        arr[i] = readLine().toInt()

        while (arr[i] >= count) {
            builder.append("+\n")
            stack.push(count++)
        }

        if (stack.peek() == arr[i]) {
            stack.pop()
            builder.append("-\n")
        } else {
            println("NO")
            break
        }
    }

    if (stack.isEmpty())
        println(builder)
}
