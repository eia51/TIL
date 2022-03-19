import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private val inorder = ArrayList<Int>()
private val postorder = ArrayList<Int>()
private lateinit var idxInorder: IntArray
private var n = 0

fun main() {
    getInput()
    solve()
    flush()
}

private fun getInput() {
    n = nextInt()
    idxInorder = IntArray(n + 1)
    inorder.add(0)
    for (i in 1..n) {
        val v = nextInt()
        inorder.add(v)
        idxInorder[v] = i
    }
    postorder.add(0)
    for (i in 1..n) {
        val v = nextInt()
        postorder.add(v)
    }
}

private fun solve() {
    makePreorder(1, n, 1, n)
    bw.write("\n")
}

private fun makePreorder(inStart: Int, inEnd: Int, postStart: Int, postEnd: Int) {
    if (inStart > inEnd || postStart > postEnd) return
    val root = postorder[postEnd]
    bw.write("$root ")

    val left = idxInorder[root] - inStart
    val right = inEnd - idxInorder[root]

    makePreorder(inStart, inStart + left - 1, postStart, postStart + left - 1)
    makePreorder(inEnd - right + 1, inEnd, postEnd - right, postEnd - 1)
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private var st = StringTokenizer("")

private fun nextInt(): Int {
    if (!st.hasMoreTokens())
        st = StringTokenizer(br.readLine(), " ")
    return st.nextToken().toInt()
}

private fun flush() {
    br.close()
    bw.flush()
    bw.close()
}
