import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val line = br.readLine()
    val stack = Stack<Char>()
    var tagStart = false
    for (ch in line) {
        when (ch) {
            '<' -> {
                while (!stack.isEmpty())
                    bw.write("${stack.pop()}")
                tagStart = true
                bw.write("$ch")
            }
            '>' -> {
                tagStart = false
                bw.write("$ch")
            }
            ' ' -> {
                while (!stack.isEmpty())
                    bw.write("${stack.pop()}")
                bw.write("$ch")
            }
            else -> {
                if (tagStart)
                    bw.write("$ch")
                else
                    stack.add(ch)
            }
        }
    }
    while (!stack.isEmpty())
        bw.write("${stack.pop()}")
    bw.write("\n")
    flush()
}

private val br = BufferedReader(InputStreamReader(System.`in`))
private val bw = BufferedWriter(OutputStreamWriter(System.out))

private fun flush() {
    bw.flush()
    bw.close()
    br.close()
}
