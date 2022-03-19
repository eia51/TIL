// #문제 스택 두개를 가지고 동작하는 큐 클래스를 만들어보시오

import java.util.*

fun main() {
    val dsq = DoubledStackQueue<Int>()
    dsq.enque(1)
    dsq.enque(2)
    dsq.enque(3)
    dsq.enque(4)
    dsq.enque(5)

    while(!dsq.isEmpty()) {
        println(dsq.deque())
    }
}

class DoubledStackQueue<T> {
    private var inStack: Stack<T> = Stack<T>()
    private var outStack: Stack<T> = Stack<T>()

    fun enque(data: T) {
        inStack.add(data)
    }

    fun deque(): T {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty())
                outStack.add(inStack.pop())
        }
        return outStack.pop()
    }

    fun isEmpty(): Boolean {
        return outStack.isEmpty() && inStack.isEmpty()
    }
}
