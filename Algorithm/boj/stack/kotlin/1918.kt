import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val exp = readLine()
    val postorder = makePostOrder(exp)
    println(postorder)
}

private fun makePostOrder(exp: String): String {
    val stack = Stack<Char>()
    val sb = StringBuilder()

    var op = ' '
    for (ch in exp) {
        if (ch.code >= 'A'.code && ch.code <= 'Z'.code)
            sb.append(ch)
        else {
            when (ch) {
                '(' -> stack.add(ch)
                ')' -> {
                    op = stack.pop()
                    while (op != '(') {
                        sb.append(op)
                        op = stack.pop()
                    }
                }
                '+', '-', '*', '/'-> {
                    while (!stack.isEmpty() && priority(stack.peek()) >= priority(ch)) {
                        op = stack.pop()
                        sb.append(op)
                    }
                    stack.add(ch)
                }
            }
        }
    }

    while (!stack.isEmpty())
        sb.append(stack.pop())

    return sb.toString()
}

private fun priority(op: Char): Int  {
    when(op) {
        '(' -> return 0
        '+', '-' -> return 1
        '*', '/' -> return 2
        ')' -> return 3
    }
    return 0
}
