fun main(){
    for(i in 0 until readLine()!!.toInt()){
        var numberOfNode = 0
        var numberOfEdge = 0
        readLine()!!.split(" ").let{
            numberOfNode = it[0].toInt()
            numberOfEdge = it[1].toInt()
        }

        val visit = Array(numberOfNode){false}
        val colors = Array(numberOfNode){-1}
        val graph = Array(numberOfNode){mutableListOf<Int>()}
        for(i in 0 until numberOfEdge){
            readLine()!!.split(" ").map{it.toInt()-1}.let{
                graph[it[0]].add(it[1])
                graph[it[1]].add(it[0])
            }
        }

        (0 until numberOfNode).forEach { node ->
            if (!visit[node]){
                visit[node] = true
                colors[node] = 0
                dfs(graph, node, visit, colors)
            }
        }

        if (isBipartite(graph, colors)){
            println("YES")
            continue
        }
        println("NO")
    }
}

fun isBipartite(graph: Array<MutableList<Int>>, colors: Array<Int>): Boolean{
    graph.indices.forEach { node ->
        graph[node].forEach { adjacent ->
            if (colors[node] == colors[adjacent]){	
                return false
            }
        }
    }

    return true
}

fun dfs(graph: Array<MutableList<Int>>, node: Int, visit: Array<Boolean>, colors: Array<Int>){
    graph[node].forEach { toNode ->
        if (!visit[toNode]){
            visit[toNode] = true
            colors[toNode] = (colors[node]+1) % 2
            dfs(graph, toNode, visit, colors)
        }
    }
}
