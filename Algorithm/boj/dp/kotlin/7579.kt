import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.max

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val N = token.nextToken().toInt()
    val M = token.nextToken().toInt()

    val m = IntArray(N + 1) { 0 }
    val c = IntArray(N + 1) { 0 }

    // m[] 초기화
    token = StringTokenizer(readLine(), " ")
    for (i in 1..N)
        m[i] = token.nextToken().toInt()

    // c[] 초기화
    token = StringTokenizer(readLine(), " ")
    for (i in 1..N)
        c[i] = token.nextToken().toInt()

    val dp = Array(N + 1) { IntArray(c.sum() + 1) { 0 } }

    var result = 0
    var isFind = false
    while (!isFind) {
        result++
        for (i in 1..N) {
            if (result < c[i]) {
                dp[i][result] = dp[i - 1][result]
            } else {
                dp[i][result] = max(dp[i - 1][result], m[i] + dp[i - 1][result - c[i]])
            }

            if (dp[i][result] >= M) {
                isFind = true
                break
            }
        }
    }
    println(result)
}
