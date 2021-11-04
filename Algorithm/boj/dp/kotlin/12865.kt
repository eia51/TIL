import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.max

// ref -> https://www.acmicpc.net/submit/12865/35045933
// 첫 코틀린 코딩테스트 풀이

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val N = token.nextToken().toInt()
    val K = token.nextToken().toInt()

    val W = IntArray(N + 1)
    val V = IntArray(N + 1)
    repeat(N) {
        token = StringTokenizer(readLine(), " ")
        W[it + 1] = token.nextToken().toInt()
        V[it + 1] = token.nextToken().toInt()
    }

    val dp = Array(N + 1) { IntArray(K + 1) { 0 } }
    for (i in 1..N) {
        for (k in 1..K) {
            if (W[i] > k) {
                dp[i][k] = dp[i - 1][k]
            } else {
                dp[i][k] = max(dp[i - 1][k], V[i] + dp[i - 1][k - W[i]])
            }
        }
    }
    println(dp[N][K])
}
