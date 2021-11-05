import java.io.BufferedReader
import java.io.InputStreamReader

// ref -> https://www.acmicpc.net/problem/9252
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val A = readLine()
    val B = readLine()

    val dp = Array(A.length + 1) { Array(B.length + 1) { 0 } }
    for (i in 1..A.length) {
        for (j in 1..B.length) {
            if (A[i - 1] == B[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
            } else {
                dp[i][j] = if (dp[i][j - 1] > dp[i - 1][j]) dp[i][j - 1] else dp[i - 1][j]
            }
        }
    }

    var i = A.length
    var j = B.length

    val LSC = StringBuilder()
    while (i > 0 && j > 0) {
        if (dp[i][j] == dp[i][j - 1]) {
            j -= 1
        } else if (dp[i][j] == dp[i - 1][j]) {
            i -= 1
        } else {
            LSC.append(A[i-1])
            i -= 1
            j -= 1
        }
    }
    println(dp[A.length][B.length])
    println(LSC.reverse())
}
