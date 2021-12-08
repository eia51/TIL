import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
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

private fun next(): Char {
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

private lateinit var chars: CharArray
private lateinit var solution: CharArray
private lateinit var visit: BooleanArray
private var L = 0
private var C = 0

fun main() {
    getInput()
    recFunc(0, 0)
    flush()
}

private fun getInput() {
    L = nextInt()
    C = nextInt()
    visit = BooleanArray(C)
    solution = CharArray(L)
    chars = CharArray(C) { next() }
    chars.sort()
}

private fun recFunc(k: Int, p: Int) {
    if (k == L) {
        var vc = 0
        var cc = 0
        for (i in 0 until L) {
            if (solution[i] == 'a' || solution[i] == 'e' || solution[i] == 'i' || solution[i] == 'o' || solution[i] == 'u') vc++
            else cc++
        }
        if (vc > 0 && cc > 1) {
            for (ch in solution)
                bw.write("$ch")
            bw.write("\n")
        }
        return
    }

    for (i in p until C) {
        if (!visit[i]) {
            visit[i] = true
            solution[k] = chars[i]
            recFunc(k + 1, i + 1)
            visit[i] = false
        }
    }
}
