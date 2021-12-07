import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*

private val bw = BufferedWriter(OutputStreamWriter(System.out))
private val br = BufferedReader(InputStreamReader(System.`in`))
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens()) {
        st = StringTokenizer(br.readLine())
    }

    return st.nextToken().toInt()
}

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}

fun main() {
    val n = nextInt()
    val operand = IntArray(n) { nextInt() }
    val operators = IntArray(4) { nextInt() }
    val solution = IntArray(n - 1)
    var minValue = Int.MAX_VALUE
    var maxValue = Int.MIN_VALUE

    fun combination(k: Int, value: Int) {
        if (k == n - 1) {
            maxValue = max(maxValue, value)
            minValue = min(minValue, value)
            return
        }

        for (cand in 0 until 4) {
            if (operators[cand] > 0) {
                operators[cand]--
                solution[k] = cand
                var newValue = value
                when (cand) {
                    0 -> newValue += operand[k + 1]
                    1 -> newValue -= operand[k + 1]
                    2 -> newValue *= operand[k + 1]
                    else -> newValue /= operand[k + 1]
                }
                combination(k + 1, newValue)
                operators[cand]++
                solution[k] = 0
            }
        }
    }
    combination(0, operand[0])
    bw.write("$maxValue\n")
    bw.write("$minValue")
    flush()
}
