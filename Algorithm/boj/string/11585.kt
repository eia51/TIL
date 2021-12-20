import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private lateinit var src: CharArray
private lateinit var pattern: CharArray
private var n = 0

fun main() {
    getInput()
    val findCnt = kmp()
    val gcd = getGcdByEuclidean(findCnt, n)
    bw.write("${findCnt / gcd}/${n / gcd}")
    flush()
}

private fun getInput() {
    n = br.readLine().toInt()
    pattern = CharArray(n)
    src = CharArray(n * 2 - 1)

    for (i in 0 until n) {
        pattern[i] = nextChar()
    }
    for (i in 0 until n) {
        val tmp = nextChar()
        src[i] = tmp
        if (i != n - 1) src[i + n] = tmp
    }
}

private fun kmp(): Int {

    fun getTable(): IntArray {
        val table = IntArray(n) { 0 }
        var pidx = 0
        for (idx in 1 until n) {
            while (pidx > 0 && pattern[idx] != pattern[pidx])
                pidx = table[pidx - 1]
            if (pattern[idx] == pattern[pidx]) {
                table[idx] = ++pidx
            }
        }
        return table
    }

    val table = getTable()
    var idx = 0
    var pidx = 0
    var match = 0
    while (idx < src.size) {
        if (src[idx] == pattern[pidx]) {
            idx++
            pidx++
        } else {
            if (pidx == 0) idx++
            else pidx = table[pidx - 1]
        }

        if (pidx == pattern.size) {
            match++
            pidx = table[pidx - 1]
        }
    }
    return match
}

fun getGcdByEuclidean(v1: Int, v2: Int): Int {
    if (v1 == v2) return v1

    var big: Int
    var small: Int
    if (v1 > v2) {
        big = v1
        small = v2
    } else {
        big = v2
        small = v1
    }

    var mod = big % small
    while (mod != 0) {
        big = small
        small = mod

        mod = big % small
        if (mod == 0)
            return small
    }
    return small
}


private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private var st = StringTokenizer("")

private fun nextChar(): Char {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")

    return st.nextToken()[0]
}

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
