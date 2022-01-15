import kotlin.math.max
import kotlin.math.min

private var maxValue = Int.MIN_VALUE
private var minValue = Int.MAX_VALUE

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    recFunc(n, 0)
    println("$minValue $maxValue")
}

private fun recFunc(num: Int, oddPrev: Int) {
    val sn = num.toString()
    val len = sn.length

    var oddTot = oddPrev
    for (ch in sn) {
        if ((ch.code - 48) % 2 != 0)
            oddTot++
    }

    // 1자리 입력 : 종료
    if (len == 1) {
        maxValue = max(maxValue, oddTot)
        minValue = min(minValue, oddTot)
        return
    }

    // 2자리 입력
    if (len == 2) {
        recFunc(num / 10 + num % 10, oddTot)
        return
    }

    // n자리 입력
    for (i in 1..len - 2) {
        for (j in i + 1..len - 1) {
            val n1 = sn.substring(0, i).toInt()
            val n2 = sn.substring(i, j).toInt()
            val n3 = sn.substring(j, len).toInt()
            recFunc(n1 + n2 + n3, oddTot)
        }
    }
}

