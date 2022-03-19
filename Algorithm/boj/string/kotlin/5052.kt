import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private lateinit var nums: Array<String>

fun main() {
    val t = br.readLine().toInt()
    for (i in 0 until t) solve()
    flush()
}

private fun solve() {
    val n = br.readLine().toInt()
    nums = Array(n) { br.readLine() }
    nums.sort()

    var findResult = false
    for (i in 1 until n) {
        if (nums[i - 1].length < nums[i].length && nums[i].startsWith(nums[i - 1])) {
            bw.write("NO\n")
            findResult = true
            break
        }
    }
    if (!findResult) {
        bw.write("YES\n")
    }
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
