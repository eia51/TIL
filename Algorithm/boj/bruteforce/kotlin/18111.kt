import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

// ref -> https://www.acmicpc.net/problem/11811
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val N = token.nextToken().toInt()
    val M = token.nextToken().toInt()
    val B = token.nextToken().toInt()

    val map = HashMap<Int, Int>()

    var cost = Int.MAX_VALUE
    var targetHeight = 0

    repeat(N) {
        token = StringTokenizer(readLine(), " ")
        repeat(M) {
            val value = token.nextToken().toInt()
            if (map.containsKey(value))
                map[value] = map[value]!! + 1
            else
                map[value] = 1
        }
    }

    for (height in 256 downTo 0) {
        var inB = B
        var inCost = 0
        for ((key, value) in map.entries) {
            if (height == key) {
                continue
            } else if (height > key) {
                val tmp = (height - key) * value
                inB -= tmp
                inCost += tmp
            } else {
                val tmp = (key - height) * value
                inB += tmp
                inCost += (tmp * 2)
            }
        }
        if (inB < 0)
            continue

        if ((cost > inCost) || ((cost == inCost) && (height > targetHeight))) {
            cost = inCost
            targetHeight = height
        }
    }

    println("$cost $targetHeight")
}
