import java.util.*
import kotlin.math.abs
import kotlin.math.max

private data class V(val x: Int, val y: Int)
private class MyComparator : Comparator<V> {
    override fun compare(o1: V, o2: V): Int {
        if (o1.y == o1.y) {
            return o1.x - o2.x
        }
        return o2.y - o1.y
    }
}

private val enemies = ArrayList<V>()
private val archerCombs = ArrayList<ArrayList<V>>()
private var n = 0
private var m = 0
private var d = 0

fun main() {
    input()
    getArcherComb()
    solve()
}

// 입력
private fun input() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer("")

    fun nextInt(): Int {
        if (!st.hasMoreTokens())
            st = StringTokenizer(readLine(), " ")
        return st.nextToken().toInt()
    }

    n = nextInt()
    m = nextInt()
    d = nextInt()
    for (i in 0 until n) {
        for (j in 0 until m) {
            val tmp = nextInt()
            if (tmp == 1)
                enemies.add(V(j, i))
        }
    }
}

// keyFunc : 궁수 조합 계산
private fun getArcherComb() {
    val sol = IntArray(3)

    fun recFunc(k: Int, p: Int) {
        if (k == 3) {
            val l = ArrayList<V>()
            for (i in 0 until 3)
                l.add(V(sol[i], n))
            archerCombs.add(l)
            return
        }

        for (i in p + 1 until m) {
            sol[k] = i
            recFunc(k + 1, i)
        }
    }

    recFunc(0, -1)
}

// 궁수 조합을 순회하면서 가장 killCount가 많은 경우의 수를 찾는다
private fun solve() {
    var maxKillCount = 0
    for (archers in archerCombs) {
        val killCount = simulation(archers)
        maxKillCount = max(maxKillCount, killCount)

    }
    println(maxKillCount)
}

// keyFunc : 만들어진 각 궁수 조합에 대해서 bfs 시뮬레이션 수행, 죽인 적의 수 반환
// 두 위치 (r1, c1), (r2, c2)의 거리는 |r1-r2| + |c1-c2|
private fun simulation(archers: ArrayList<V>): Int {
    var stage = 0
    var killCount = 0
    val enemyList = ArrayList<V>()
    val attackSet = HashSet<V>()

    fun aim() {
        enemyList.sortWith(MyComparator())

        for (archer in archers) {
            var minDist = Int.MAX_VALUE
            var target: V? = null

            for (enemy in enemyList) {
                val dist = abs(enemy.x - archer.x) + abs(enemy.y + stage - archer.y)
                if (dist < minDist && dist <= d) {
                    minDist = dist
                    target = enemy
                }
            }
            if (target != null) {
                attackSet.add(target)
            }
        }
    }

    fun attack() {
        for (target in attackSet) {
            if (target in enemyList) {
                killCount++
                enemyList.remove(target)
            }
        }
    }

    fun move() {
        stage++
        for (i in enemyList.size - 1 downTo 0) {
            val enemy = enemyList[i]
            if (enemy.y + stage >= n)
                enemyList.removeAt(i)
        }
    }

    // initialize
    for (enemy in enemies)
        enemyList.add(enemy)

    // real-simulation
    while (enemyList.isNotEmpty()) {
        aim()
        attack()
        move()
    }

    return killCount
}
