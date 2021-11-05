import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Integer.max

// ref -> https://www.acmicpc.net/problem/9251
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val inputA = readLine()
    val inputB = readLine()

    val LCS = Array(inputA.length + 1) { IntArray(inputB.length + 1) { 0 } }

    for (i in inputA.indices) {
        for (j in inputB.indices) {
            LCS[i + 1][j + 1] = if (inputA[i] == inputB[j])
                LCS[i][j] + 1
            else
                max(LCS[i][j + 1], LCS[i + 1][j])
        }
    }

    println(LCS[inputA.length][inputB.length])
}
