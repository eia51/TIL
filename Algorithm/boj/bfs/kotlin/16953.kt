import java.util.*

fun main() {
    val sc = Scanner(System.`in`)
    val inp = sc.nextLine().split(" ").map { it.toLong() }
    bfsFunc(inp[0], inp[1])
}

private fun bfsFunc(src: Long, goal: Long) {
    val dq = ArrayDeque<Point>()
    dq.add(Point(src, 1))

    while (!dq.isEmpty()) {
        val item = dq.poll()
        if (item.x == goal) {
            println(item.age)
            return
        }
        if (item.x > goal) {
            continue
        }

        val v1 = item.x * 2
        dq.addLast(Point(v1, item.age + 1))
        val v2 = item.x * 10 + 1
        dq.addLast(Point(v2, item.age + 1))
    }
    println(-1)
}

private data class Point(val x: Long, val age: Int)
