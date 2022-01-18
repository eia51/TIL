import java.util.*

private lateinit var arr: LongArray
private lateinit var dp: LongArray
private var n = 0
private var k = 0

fun main() {
    input()
    solve()
}

private fun solve() {
    var L = 0
    var sum = 0L

    // 1. (L+1..R)에 해당하는 블록에서의 만족도를 계산한다.
    // 2. R을 우변으로 갖는 블록들에 대하여,
    //     L+1~R-1에 범위 내에서 sum이 k를 넘어서는 값들 중 가장 큰 값으로 다이나믹 테이블을 만든다.
    //     이는 R 위치까지의 만족도 중 가장 큰 값이 된다.
    for (R in 1..n) {
        sum += arr[R]

        // sum이 k를 넘지 못했을 때는 이전 만족도를 유지한다.
        dp[R] = dp[R - 1]

        // sum이 k를 넘어서는 경우, sum이 k 아래로 떨어질 때 까지
        // L을 한칸씩 밀면서 다이나믹 테이블을 채워나간다.
        //  * dp[L]은 이전 구간의 만족도,
        //    satisfy = (nL ~ R) 즉, 현재 확인하고 있는 구간의 만족도
        //
        //    dp[R]은 dp[R-1]의 값을 초기에 가지고 있다가,
        //    R 고정, L이 우측으로 1씩 증가하면서 변화하는 블록들 중 가장 높은 만족도의 합으로 값이 업데이트 된다.
        while (sum >= k) {
            val satisfy = sum - k
            dp[R] = kotlin.math.max(dp[R], dp[L] + satisfy)
            sum -= arr[++L]
        }
    }
    println(dp[n])
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    fun nextLong(): Long {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toLong()
    }

    n = nextInt()
    k = nextInt()
    arr = LongArray(n + 1)
    dp = LongArray(n + 1)

    for (i in 1..n) arr[i] = nextLong()
}
