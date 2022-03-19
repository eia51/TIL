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


/*
 * Complete the 'solution' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts INTEGER_ARRAY cards as parameter.
 */

fun solution(cards: Array<Int>): Int {
    // convert array to list for operation
    val list = cards.toMutableList()

    for (i in 0 until cards.size) {
        // remove
        val item = list.removeAt(i)

        // check answer
        var odd = 0
        var even = 0
        for (j in 0 until list.size) {
            if (j % 2 == 0) {
                even += (list.get(j))
                // println("E add ) ${list.get(j)}")
            }
            else  {
                odd += (list.get(j))
                // println("O add ) ${list.get(j)}")
            }
        }

        // println("Result, odd=${odd}, even=${even}")
        if (odd == even) {
            return i + 1
        }

        // if not find answer, rollback
        list.add(i, item)
    }

    return -1
}
fun main(args: Array<String>) {
    val cardsCount = readLine()!!.trim().toInt()

    val cards = Array<Int>(cardsCount, { 0 })
    for (i in 0 until cardsCount) {
        val cardsItem = readLine()!!.trim().toInt()
        cards[i] = cardsItem
    }

    val result = solution(cards)

    println(result)
}
