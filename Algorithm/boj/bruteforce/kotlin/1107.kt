import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


val CURRENT = 100
val KEYS = IntArray(10) { 1 }

// ref -> https://www.acmicpc.net/problem/1107
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val channelString = readLine()
    val channel = channelString.toInt()
    val n = readLine().toInt()
    if (n > 0) {
        val token = StringTokenizer(readLine(), " ")
        for (i in 0 until n)
            KEYS[token.nextToken().toInt()] = 0
    }

    if (channel == CURRENT) {
        println(0)
        return
    }

    val gap = abs(CURRENT - channel)
    if (gap <= channelString.length || n == 10) {
        println(gap)
        return
    }

    if (isAvailable(channel)) {
        println(channelString.length)
        return
    }

    var offset = 1
    while (true) {
        val less = max(channel - offset, 0)
        if (isAvailable(less)) {
            printCount(less, channel, gap)
            break
        }
        val more = channel + offset
        if (isAvailable(more)) {
            printCount(more, channel, gap)
            break
        }
        offset++
    }
}

fun isAvailable(channel: Int): Boolean {
    var tmp: Int = channel
    if (channel == 0)
        return KEYS[channel] == 1

    while (tmp != 0) {
        if (KEYS[tmp.mod(10)] == 0)
            return false
        tmp = tmp.div(10)
    }
    return true
}

fun printCount(target: Int, origin: Int, gap: Int) {
    val targetStr = target.toString()
    val count = min(targetStr.length + abs(target - origin), gap)
    println(count)
}
