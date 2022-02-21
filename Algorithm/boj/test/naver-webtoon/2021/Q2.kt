// #문제 abeabcaaba 와 같이 문자열 입력이 주어질 때, c1e1b3a5 와 같은 결과를 출력하시오

data class Edge(val ch: Char, val cnt: Int)

fun main() {
    val input = "abeabcaaba"
    val list = parseArrayList(input)
    list.quickSort()

    println(list)
}

// 1. 입력 문자열을 {문자, 개수} 형태의 객체를 담는 배열로 변환
private fun parseArrayList(input: String): ArrayList<Edge> {
    val cntMap = HashMap<Char, Int>()
    for (ch in input) {
        if (!cntMap.containsKey(ch))
            cntMap[ch] = 1
        else
            cntMap[ch] = cntMap[ch]!! + 1
    }

    val list = ArrayList<Edge>()

    for (k in cntMap.keys)
        list.add(Edge(k, cntMap[k]!!))

    return list
}

// 2. Quick sort 수행
fun ArrayList<Edge>.quickSort(start: Int = 0, end: Int = this.size - 1) {
    if (start >= end) return
    val mid = partition(this, start, end)
    quickSort(start, mid - 1)
    quickSort(mid, end)
}

// 2_1. Quick sort를 위한 파티셔닝
fun partition(list: ArrayList<Edge>, start: Int, end: Int): Int {
    var left = start
    var right = end
    val piv = list[(start+end)/2]

    while(left <= right) {
        while(myCompare(list[left], piv))
            left++

        while(myCompare(piv, list[right]))
            right--

        if (left<= right) {
            list[left] = list[right].also { list[right] = list[left] }
            left++
            right--
        }
    }

    return left
}

// 2_2. Quick sort를 위한 정렬조건 부여
fun myCompare(v1: Edge, v2: Edge): Boolean {
    //여기서 1차 우선순위를 갖는 정렬 조건을 부여
    if (v2.cnt > v1.cnt)
        return true
    else if (v1.cnt > v2.cnt)
        return false
    else {
        //여기서 2차 우선순위를 갖는 정렬 조건을 부여
        return false
    }
}
