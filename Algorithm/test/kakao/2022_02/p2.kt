import java.io.*
import java.math.*
import java.security.*
import java.text.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.regex.*
import java.util.stream.*
import kotlin.collections.*
import kotlin.comparisons.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.jvm.functions.*
import kotlin.jvm.internal.*
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*

fun solution(w: Array<Float>): Int {
    var movingCnt = 0

    val weights = w.toMutableList()
    weights.sort()

    while (!weights.isEmpty()) {
        var sum = 0.0
        for (i in weights.size - 1 downTo 0) {
            sum += weights[i]
            if (sum > 3.0) {
                sum -= weights[i]
                continue
            }
            weights.removeAt(i)
        }
        movingCnt++
    }
    return movingCnt
}

fun main(args: Array<String>) {
    val weightCount = readLine()!!.trim().toInt()

    val weight = Array<Float>(weightCount, { 0f })
    for (i in 0 until weightCount) {
        val weightItem = readLine()!!.trim().toFloat()
        weight[i] = weightItem
    }

    val result = solution(weight)

    println(result)
}
