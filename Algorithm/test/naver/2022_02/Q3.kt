import kotlin.math.max
import kotlin.math.min

fun solution(A: IntArray): Int {
    var maxValue = Int.MIN_VALUE
    var minValue = Int.MAX_VALUE

    fun needCut(start: Int): Boolean {
        var maxCheck = false
        var minCheck = true
        for (i in start until A.size) {
            if (maxValue > A[i])
                maxCheck = true
            if (minValue > A[i])
                minCheck = false
        }
        return maxCheck && minCheck
    }

    fun pro(): Int {
        var prev = -1
        val ans = ArrayList<Int>()
        for (i in A.indices) {
            maxValue = max(maxValue, A[i])
            minValue = min(minValue, A[i])
            if (needCut(i)) {
                if (i == A.size - 1 && ans.isEmpty()) {
                    println(listOf(1))
                    return 1
                }

                val v = i - prev
                ans.add(v)
                prev = i
                maxValue = Int.MIN_VALUE
                minValue = Int.MAX_VALUE
            }
        }
        println(ans.toList())
        ans.sort()

        return ans[ans.size - 1]
    }

    return pro()
}

fun main() {
    val inputs = arrayOf(
        intArrayOf(2, 4, 1, 6, 5, 9, 7),
        intArrayOf(2, 1, 6, 4, 3, 7),
        intArrayOf(4, 3, 2, 6, 1),
    )

    val outputs = intArrayOf(3, 3, 1)

    for (i in 0 until 3) {
        println("#${i + 1} Test input : ${inputs[i].toList()}")
        val result = solution(inputs[i])
        if (result == outputs[i]) {
            println("=========================================")
            println("#${i + 1} Result : (SUCCESS) ans=$result, expect=${outputs[i]}")
            println("=========================================\n")
        } else {
            println("=========================================")
            println("#${i + 1} Result : (FAIL) ans=$result, expect=${outputs[i]}")
            println("=========================================\n")
            throw Exception("Wrong answer. ans=$result, expect=${outputs[i]}")
        }
    }
}

