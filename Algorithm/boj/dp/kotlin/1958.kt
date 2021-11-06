package Algorithm

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

// ref -> https://www.acmicpc.net/problem/1958
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val A = readLine()
    val B = readLine()
    val C = readLine()
    val LCS = Array(A.length + 1) { Array(B.length + 1) { IntArray(C.length + 1) { 0 } } }

    for (i in 1..A.length) {
        for (j in 1..B.length) {
            for (k in 1..C.length) {
                LCS[i][j][k] =
                    if ((A[i-1] == B[j-1]) && (A[i-1] == C[k-1]))
                        LCS[i - 1][j - 1][k - 1] + 1
                    else
                        max(max(LCS[i-1][j][k], LCS[i][j-1][k]), LCS[i][j][k-1])
            }
        }
    }
    println(LCS[A.length][B.length][C.length])
}
