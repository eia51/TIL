import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val tree = HashMap<String, Array<String>>()

    fun getInput() {
        val n = readLine().toInt()
        repeat(n) {
            val token = StringTokenizer(readLine(), " ")
            val p = token.nextToken()
            val ln = token.nextToken()
            val rn = token.nextToken()
            tree[p] = arrayOf(ln, rn)
        }
    }

    fun preorder(node: String) {
        if (node == ".")
            return

        bw.write(node)
        preorder(tree[node]!![0])
        preorder(tree[node]!![1])
    }

    fun inorder(node: String) {
        if (node == ".")
            return

        inorder(tree[node]!![0])
        bw.write(node)
        inorder(tree[node]!![1])
    }

    fun postorder(node: String) {
        if (node == ".")
            return

        postorder(tree[node]!![0])
        postorder(tree[node]!![1])
        bw.write(node)
    }

    getInput()

    preorder("A")
    bw.write("\n")

    inorder("A")
    bw.write("\n")

    postorder("A")
    bw.write("\n")

    bw.flush()
    bw.close()
}
