import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var token = StringTokenizer(readLine(), " ")
    val v = token.nextToken().toInt()
    val e = token.nextToken().toInt()

    val k = readLine().toInt()
    val distances = IntArray(v + 1) { Int.MAX_VALUE }

    val graph = HashMap<Int, ArrayList<Edge>>()
    for (i in 0 until e) {
        token = StringTokenizer(readLine(), " ")
        val p = token.nextToken().toInt()
        val ch = token.nextToken().toInt()
        val w = token.nextToken().toInt()

        if (!graph.containsKey(p))
            graph[p] = ArrayList()

        graph[p]?.add(Edge(ch, w))
    }

    dijkstra(distances, k, v, graph)

    printResult(distances)
}

private fun printResult(distances: IntArray) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    for (i in 1 until distances.size) {
        val it = distances[i]
        if (it == Int.MAX_VALUE)
            bw.write("INF\n")
        else
            bw.write("$it\n")
    }
    bw.flush()
    bw.close()
}

private fun dijkstra(distances: IntArray, start: Int, size:Int, graph: HashMap<Int, ArrayList<Edge>>) {
    val visit = BooleanArray(size+1)
    val pq = PriorityQueue<Edge>()
    distances[start] = 0
    pq.add(Edge(start, 0))

    while (!pq.isEmpty()) {
        val edge = pq.poll()

        if (visit[edge.v])
            continue

        visit[edge.v] = true

        if (!graph.containsKey(edge.v))
            continue

        for (child in graph[edge.v]!!) {
            if (distances[child.v] > child.d + distances[edge.v]) {
                distances[child.v] = child.d + distances[edge.v]
                pq.offer(Edge(child.v, distances[child.v]))
            }
        }
    }
}


private data class Edge(val v: Int, val d: Int) : Comparable<Edge> {
    override fun compareTo(other: Edge): Int {
        return this.d - other.d
    }
}
