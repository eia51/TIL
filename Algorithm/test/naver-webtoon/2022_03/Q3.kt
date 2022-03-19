package 네이버웹툰

// 3회차 라이브 코딩테스트 - 장진수
//[코딩테스트]
//
//두 배열을 전달받아 배열을 구성하고 있는 숫자가 같은지 체크하는 함수를 구현하세요
//- 정수형 배열입니다.
//- 배열은 매우 길 수 있습니다.
//
//ex) 
//
//val array1 = intArrayOf(2, 5, 8, 4, 10, 4, 9, 3, 2)
//val array2 = intArrayOf(2, 9, 4, 10, 8, 9, 5, 3)
//
//val result = isSame(array1, array2)    // 예제에서 결과는 true 가 나옵니다.
fun main() {
    val array1 = intArrayOf(2, 5, 8, 4, 10, 4, 9, 3, 2)
    val array2 = intArrayOf(2, 9, 4, 10, 8, 9, 5, 3)

    if (isSame(array1, array2))
        println("is same")
    else
        println("isn't same")
}

private fun isSame(arr1: IntArray, arr2: IntArray): Boolean {
    val newArr1 = arr1.toSortedSet().toIntArray()
    val newArr2 = arr2.toSortedSet().toIntArray()

    if (newArr1.size != newArr2.size)
        return false

    for (i in newArr1.indices) {
        if (newArr1[i] != newArr2[i])
            return false
    }

    return true
}