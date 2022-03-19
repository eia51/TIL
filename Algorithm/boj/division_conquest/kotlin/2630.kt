import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private var white = 0
private var blue = 0
private var n = 0
private lateinit var arr: Array<IntArray>

fun main() {
    val bcwc = getInput()
    dc(0, 0, n, bcwc)
    flush()
}

private fun getInput(): IntArray {
    val bcwc = IntArray(2)
    n = nextInt()
    arr = Array(n) { IntArray(n) }
    for (i in 0 until n) {
        for (j in 0 until n) {
            val color = nextInt()
            bcwc[color]++
            arr[i][j] = color
        }
    }
    return bcwc
}

private fun dc(x: Int, y: Int, size: Int, bcwc: IntArray) {
    if (bcwc[0] == 0 || bcwc[1] == 0) {
        if (bcwc[0] == 0) blue++
        else white++
        return
    }

    val mid = size / 2
    if (mid <1) return

    val bcwc1 = IntArray(2)
    val bcwc2 = IntArray(2)
    val bcwc3 = IntArray(2)
    val bcwc4 = IntArray(2)

    for (i in 0 until size) {
        for (j in 0 until size) {
            val nx = i + x
            val ny = j + y

            val color = arr[nx][ny]
            if (i < mid && j < mid) bcwc2[color]++
            else if (i >= mid && j < mid) bcwc1[color]++
            else if (i >= mid && j >= mid) bcwc4[color]++
            else bcwc3[color]++
        }
    }
    dc(x + mid, y, mid, bcwc1)
    dc(x, y, mid, bcwc2)
    dc(x, y + mid, mid, bcwc3)
    dc(x + mid, y + mid, mid, bcwc4)
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private var st = StringTokenizer("")

private fun flush() {
    bw.write("$white\n$blue\n")
    bw.flush()
    bw.close()
    br.close()
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}
