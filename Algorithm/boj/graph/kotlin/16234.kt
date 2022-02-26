import java.util.*

private data class Country(val y: Int, val x: Int)

private var n = 0
private var l = 0
private var r = 0
private var isCombine = false
private var grpId = 1
private var grpMap = HashMap<Int, ArrayList<Country>>()
private lateinit var region: Array<IntArray>
private lateinit var belongGrp: Array<BooleanArray>
private val dy = intArrayOf(1, -1, 0, 0)
private val dx = intArrayOf(0, 0, 1, -1)

fun main() {
    input()
    solve()
}

private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    l = nextInt()
    r = nextInt()

    region = Array(n) { IntArray(n) { nextInt() } }
    belongGrp = Array(n) { BooleanArray(n) { false } }
}


private fun solve() {
    var round = 1

    while (true) {
        if (!composeGrp()) break
        migration()
        round++
    }
    println(round-1)
}

private fun composeGrp(): Boolean {
    initGrp()

    for (i in 0 until n) {
        for (j in 0 until n) {
            if (belongGrp[i][j])
                continue

            dfs(i, j)
            grpId++
        }
    }
    return isCombine
}

private fun initGrp() {
    isCombine = false
    grpId = 0
    grpMap.clear()

    for (i in 0 until n) {
        for (j in 0 until n) {
            belongGrp[i][j] = false
        }
    }
}

private fun dfs(y: Int, x: Int) {
    belongGrp[y][x] = true
    if (!grpMap.containsKey(grpId))
        grpMap[grpId] = ArrayList()
    grpMap[grpId]?.add(Country(y, x))

    for (i in 0 until 4) {
        val ny = y + dy[i]
        val nx = x + dx[i]

        if (ny !in 0 until n || nx !in 0 until n || belongGrp[ny][nx])
            continue

        val gap = kotlin.math.abs(region[y][x] - region[ny][nx])
        if (gap in l..r) {
            isCombine = true
            dfs(ny, nx)
        }
    }
}

private fun migration() {
    for (key in grpMap.keys) {
        val list = grpMap[key] ?: ArrayList()
        var population = 0
        for (country in list)
            population += region[country.y][country.x]

        population /= list.size
        for (country in list)
            region[country.y][country.x] = population
    }
}16234.
