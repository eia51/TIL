package str

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val s = readLine()
    val t = readLine().toMutableList()

    while (s.length != t.size) {
        when (t.last()) {
            'A' -> {
                t.removeLast()
            }
            'B' -> {
                t.removeLast()
                t.reverse()
            }
        }
    }
    val sb = StringBuilder()
    for (ch in t) sb.append(ch)

    if (s == sb.toString())
        println(1)
    else
        println(0)
}

