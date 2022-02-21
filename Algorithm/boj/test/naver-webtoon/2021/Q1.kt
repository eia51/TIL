// #문제 input, answer이 다음과 같이 주어질 때, input의 각 요소를 더했을 때 answer이 나오는 경우의 인덱스를 출력하시오
//   요구하는 정답의 경우는 반드시 한 개만 존재

fun main() {
    val input = intArrayOf(2,4,6,3,8)
    val answer = 7

    solve(input, 7)  // expect-> (1,3)
    solve(input, 6)  // expect-> (0,1)
    solve(input, 10) // expect-> (1,2)
}

fun solve(input: IntArray, answer: Int): Unit {
    val map = HashMap<Int, Int>()
    for (i in input.indices) {
        if (map.containsKey(answer - input[i])) {
            println("(${map[answer - input[i]]},$i)")
            return
        }

        map[input[i]] = i
    }
    println("ERR")
}
