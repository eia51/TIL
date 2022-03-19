package 기업코테.네이버웹툰.경력공채_2022_03

private data class Item(val fq: Int, val num: Int)

// 2회차 라이브 코딩테스트수 - 송택일
fun main() {
    val inp = intArrayOf(3, 1, 2, 2, 4)

    val map = HashMap<Int, Int>()
    for (num in inp)
        map[num] = (map[num] ?: 0) + 1

    val arr = ArrayList<Item>(map.size)
    for (key in map.keys)
        arr.add(Item(map[key] ?: 0, key))

//    arr.quickSort()
    arr.bubbleSort()

    for (item in arr) {
        repeat(item.fq) {
            println(item.num)
        }
    }
}

private fun ArrayList<Item>.bubbleSort() {
    val list = this
    val size = list.size

    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            if (itemCompare(list[j], list[i])) {
                list[j] = list[i].also { list[i] = list[j] }
            }
        }
    }
}

private fun ArrayList<Item>.quickSort(start: Int = 0, end: Int = this.size - 1) {
    if (start >= end) return
    val mid = partitionNode(this, start, end)
    quickSort(start, mid - 1)
    quickSort(mid, end)
}

private fun partitionNode(arr: ArrayList<Item>, left: Int, right: Int): Int {
    var start = left
    var end = right
    val piv = arr[(start + end) / 2]

    while (start <= end) {
        while (itemCompare(arr[start], piv))
            start++

        while (itemCompare(piv, arr[end]))
            end--

        if (start <= end) {
            arr[start] = arr[end].also { arr[end] = arr[start] }
            start++
            end--
        }
    }
    return start
}

private fun itemCompare(item1: Item, item2: Item): Boolean {
    return if (item1.fq < item2.fq) true
    else if (item1.fq > item2.fq) false
    else item1.num < item2.num
}