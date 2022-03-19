package 기업코테.네이버웹툰.경력공채_2022_03

import java.util.*

// 1회차 라이브 코딩테스트 - 임승택
fun main() {

}

class DoubleStackedQueue<T> {
    private val inStack = Stack<T>()
    private val outStack = Stack<T>()

    fun isEmpty(): Boolean {
        return outStack.isEmpty() && inStack.isEmpty()
    }

    fun enqueue(data: T) {
        inStack.push(data)
    }

    fun dequeue(): T {
        if (isEmpty())
            throw Exception("Empty Queue")

        if (outStack.isEmpty())
            while (inStack.isNotEmpty())
                outStack.push(inStack.pop())

        return outStack.pop()
    }
}
