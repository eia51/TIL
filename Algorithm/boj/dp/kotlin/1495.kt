import java.util.*

private lateinit var arr: IntArray
private lateinit var dp: Array<IntArray>
private var n = 0
private var s = 0
private var m = 0

fun main() {
    input()
    solve()
}

private fun solve() {
    dp[0][s] = 1

    var valid: Int
    var next: Int
    for (i in 1..n) {
        valid = 0
        for (j in 0..m) {
            if (dp[i - 1][j] == 0)
                continue

            next = j + arr[i]
            if (next in 0..m) {
                dp[i][next] = 1
                valid++
            }

            next = j - arr[i]
            if (next in 0..m) {
                dp[i][next] = 1
                valid++
            }
        }

        // 중간 볼륨 조절 불가
        if (valid == 0) {
            println(-1)
            return
        }
    }

    // 마지막 곡에 대한 dp 테이블에서 가장 높은 유효 인덱스 출력
    for (i in m downTo 0) {
        if (dp[n][i] != 0) {
            println(i)
            return
        }
    }
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    s = nextInt()
    m = nextInt()

    arr = IntArray(n + 1)
    for (i in 1..n) arr[i] = nextInt()

    dp = Array(n + 1) { IntArray(m + 1) { 0 } }
}
