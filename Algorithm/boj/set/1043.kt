import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private var n = 0
private var m = 0
private var k = 0
private var know = Stack<Int>()
private lateinit var parties: Array<MutableSet<Int>>
private lateinit var partiesCheck: BooleanArray
private lateinit var visit: BooleanArray

fun main() {
    getInput()
    solve()
    flush()
}

private fun getInput() {
    n = nextInt()
    m = nextInt()
    k = nextInt()
    visit = BooleanArray(n) { false }
    repeat(k) {
        val knowPerson = nextInt()
        know.add(knowPerson)
        visit[knowPerson - 1] = true
    }
    partiesCheck = BooleanArray(m)
    parties = Array(m) { mutableSetOf() }
    for (i in 0 until m) {
        val guest = nextInt()
        repeat(guest) {
            parties[i].add(nextInt())
        }
    }
}

private fun solve() {
    var truthParties = 0
    while (!know.isEmpty()) {
        val knowPerson = know.pop()
        val candidates = mutableSetOf<Int>()

        for (partyIdx in 0 until m) {
            if (partiesCheck[partyIdx]) {
                continue
            }

            if (knowPerson in parties[partyIdx]) {
                partiesCheck[partyIdx] = true
                truthParties++
                for (guest in parties[partyIdx])
                    candidates.add(guest)
            }
        }

        for (guest in candidates) {
            if (!visit[guest - 1]) {
                know.add(guest)
                visit[guest - 1] = true
            }
        }
    }
    bw.write("${m - truthParties}\n")
}


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
