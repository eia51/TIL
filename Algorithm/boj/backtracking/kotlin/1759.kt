import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private val bw = BufferedWriter(OutputStreamWriter(System.out))
private val br = BufferedReader(InputStreamReader(System.`in`))
private var st = StringTokenizer("")

private lateinit var chars: CharArray
private lateinit var select: CharArray
private lateinit var visit: BooleanArray

private var L = 0
private var C = 0

fun main() {
    getInput()
    recFunc(0, 0, 0, 0)
    flush()
}

private fun getInput() {
    L = nextInt()
    C = nextInt()
    chars = CharArray(C) { nextChar() }
    visit = BooleanArray(C)
    select = CharArray(L)
    chars.sort()
}

private fun recFunc(digit: Int, start: Int, v1: Int, v2: Int) {
    if (digit == L) {
        if (v1 >= 1 && v2 >= 2) {
            for (ch in select)
                bw.write("$ch")
            bw.write("\n")
        }
        return
    }

    for (i in start until C) {
        if (!visit[i]) {
            visit[i] = true
            select[digit] = chars[i]
            if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u')
                recFunc(digit + 1, i + 1, v1 + 1, v2)
            else
                recFunc(digit + 1, i + 1, v1, v2 + 1)
            visit[i] = false
        }
    }
}

private fun nextInt(): Int {
    if (!st.hasMoreTokens()) {
        st = StringTokenizer(br.readLine())
    }

    return st.nextToken().toInt()
}

private fun nextChar(): Char {
    if (!st.hasMoreTokens()) {
        st = StringTokenizer(br.readLine())
    }

    return st.nextToken()[0]
}

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
