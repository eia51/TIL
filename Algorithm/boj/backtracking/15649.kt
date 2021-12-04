import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    P15649().solve()
}

class P15649 {
    private val scan = FastReader()
    private val bw = BufferedWriter(OutputStreamWriter(System.out))

    lateinit var ans: IntArray
    lateinit var select: BooleanArray
    var n = 0
    var m = 0

    fun solve() {
        n = scan.nextInt()
        m = scan.nextInt()
        ans = IntArray(m + 1)
        select = BooleanArray(n + 1)

        recFunc(1)

        bw.flush()
        bw.close()
    }

    fun recFunc(k: Int) {
        if (k == m + 1) {
            for (i in 1..m)
                bw.write("${ans[i]} ")
            bw.write("\n")
        } else {
            for (cand in 1..n) {
                if (select[cand]) continue
                select[cand] = true
                ans[k] = cand
                recFunc(k + 1)
                select[cand] = false
            }
        }
    }

    private class FastReader {
        private var br: BufferedReader = BufferedReader(InputStreamReader(System.`in`))
        private var st: StringTokenizer? = null

        fun next(): String {
            while (st == null || !st!!.hasMoreElements()) {
                st = StringTokenizer(br.readLine(), " ")
            }
            return st!!.nextToken()
        }

        fun nextInt(): Int {
            return next().toInt()
        }

        fun nextLong(): Long {
            return next().toLong()
        }

        fun nextDouble(): Double {
            return next().toDouble()
        }

        fun nextLine(): String {
            return br.readLine()
        }
    }
}
