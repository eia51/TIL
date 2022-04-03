private data class Chars(val ch: Char, val weight: Int) : Comparable<Chars> {
    override fun compareTo(other: Chars): Int {
        return other.weight - weight
    }
}

private lateinit var words: Array<CharArray>
private val weightMap = HashMap<Char, Int>()
private val charsList = ArrayList<Chars>()
private var ans = 0
private var n = 0

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    n = readLine().toInt()
    words = Array(n) { readLine().toCharArray() }
}

private fun solve() {
    makeCountMap()
    sortCountMapByCntDesc()
    calculate()
    printAnswer()
}

private fun makeCountMap() {
    fun pow(base: Int, unit: Int): Int {
        var ans = 1
        repeat(unit) {
            ans *= base
        }
        return ans
    }

    for (i in 0 until n) {
        var unit = words[i].size - 1
        for (j in 0 until words[i].size) {
            val weight = pow(10, unit--)
            weightMap[words[i][j]] = (weightMap[words[i][j]] ?: 0) + weight
        }
    }
}

private fun sortCountMapByCntDesc() {
    for (key in weightMap.keys)
        charsList.add(Chars(key, weightMap[key] ?: 0))
    charsList.sort()
}

private fun calculate() {
    var unit = 9
    for (item in charsList)
        ans += (unit-- * item.weight)
}

private fun printAnswer() {
    println(ans)
}
