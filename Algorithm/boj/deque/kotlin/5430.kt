import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.collections.ArrayDeque

private lateinit var cmds: String
private lateinit var nums: ArrayDeque<String>
private var n = 0

fun main() {
    val t = nextInt()
    for (i in 1..t) {
        getInput()
        solve()
    }
    flush()
}

private fun getInput() {
    cmds = nextLine()
    n = nextInt()
    val numStr = nextLine()
    nums = ArrayDeque(n)
    nums.addAll(numStr.substring(1, numStr.length - 1).split(","))
}

private fun solve() {
    var isReverse = false
    for (cmd in cmds) {
        if (cmd == 'R') {
            isReverse = !isReverse
        } else {
            if (nums.size <= 0 || n == 0) {
                bw.write("error\n")
                return
            }

            if (isReverse)
                nums.removeLast()
            else
                nums.removeFirst()
        }
    }
    if (isReverse)
        nums.reverse()

    printResult()
}

private fun printResult() {
    bw.write("[")
    for (i in nums.indices) {
        bw.write(nums[i])
        if (i != nums.size - 1) bw.write(",")
    }
    bw.write("]\n")
}

private val bw = BufferedWriter(OutputStreamWriter(System.out))
private val br = BufferedReader(InputStreamReader(System.`in`))
private var st = StringTokenizer("")

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")

    return st.nextToken().toInt()
}

private fun nextLine(): String {
    return br.readLine()
}
