import java.util.*
import kotlin.math.abs
import kotlin.math.min

private data class Point(val x: Int, val y: Int)

private lateinit var solution: IntArray
private val houses = ArrayList<Point>()
private val chickens = ArrayList<Point>()
private var n = 0
private var m = 0
private var minDist = Int.MAX_VALUE

fun main() {
    input()
    makeCandidate(0, 0)
    println(minDist)
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    m = nextInt()
    solution = IntArray(m)

    for (i in 0 until n) {
        for (j in 0 until n) {
            when (nextInt()) {
                1 -> houses.add(Point(i, j))
                2 -> chickens.add(Point(i, j))
            }
        }
    }
}

private fun makeCandidate(k: Int, l: Int) {
    if (k == m) {
        calcDist()
        return
    }

    for (i in l until chickens.size) {
        solution[k] = i
        makeCandidate(k + 1, i + 1)
    }
}

private fun calcDist() {
    var curDist = 0
    for (house in houses) {
        var dist = Int.MAX_VALUE
        for (chickenIdx in solution) {
            val chicken = chickens[chickenIdx]
            dist = min(dist, abs(house.x - chicken.x) + abs(house.y - chicken.y))
        }
        curDist += dist
    }
    minDist = min(minDist, curDist)
}
