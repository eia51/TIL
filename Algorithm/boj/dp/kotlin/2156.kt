import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val bucket = IntArray(n) { readLine().toInt() }

    // dp[i][j]
    // i : 0=직전포함, 1=직전1개스킵, 2=직전2개스킵
    // j : =n
    val dp = Array(3) { IntArray(n + 1) }
    dp[0][1] = bucket[0]
    dp[1][1] = bucket[0]

    for (i in 2..n) {
        dp[0][i] = max(dp[1][i - 1], dp[2][i - 1]) + bucket[i - 1]
        dp[1][i] = max(dp[0][i - 2], dp[1][i - 2]) + bucket[i - 1]
        if (i >= 3) {
            dp[2][i] = max(max(dp[0][i - 3], dp[1][i - 3]), dp[2][i - 3]) + bucket[i - 1]
        }
    }

    // 정답 후보군은 dp[0][n], dp[1][n], dp[0][n-1] 범위에서 가장 큰 값을 갖는 경우이다.
    // dp[2][n]의 경우, dp[1][n-1]의 경우에는 더 많은 포도주를 마실 수 있는 경우의 수가 포함되기 때문에
    // bucket[i]가 모두 양수인 이 문제에선 가장 큰 수를 기대할 수 없는 범위이다.
    val cand1 = max(dp[0][n], dp[1][n])
    val cand2 = dp[0][n - 1]
    println(max(cand1, cand2))
}

