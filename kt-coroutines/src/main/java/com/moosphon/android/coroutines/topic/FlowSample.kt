package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 先前示例中的 flow { ... } 构建器是最基础的一个。还有其他构建器使流的声明更简单：
 * flowOf 构建器定义了一个发射固定值集的流。
 * 使用 .asFlow() 扩展函数，可以将各种集合与序列转换为流。
 */
fun simple() = flow<Int> {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        // 发射数据
        emit(i)
    }
}

/**
 * 不阻塞主线程的情况下每等待 100 毫秒打印一个数字。
 */
//fun main() = runBlocking {
//    println("Calling simple function...")
//    val flow = simple()
//    println("Calling collect...")
//    // 数据接收
//    flow.collect{
//        value ->
//        println(value)
//    }
//    println("Flow done")
//}

//*****************  流操作符 *******************


/**
 * 模拟请求操作
 */
suspend fun mockRequest(id: Int): String {
    delay(1000)
    return "response of [$id]"
}

/**
 * 过渡流操作符
 * 可以使用操作符转换流，就像使用集合与序列一样。
 * 过渡操作符应用于上游流，并返回下游流。
 * 这些操作符也是冷操作符，就像流一样。这类操作符本身不是挂起函数。
 * 它运行的速度很快，返回新的转换流的定义。
 */
//fun main() = runBlocking {
//    // asFlow会自动发送数据
//    (1..5).asFlow()
//        .map { id -> mockRequest(id) }
//        .collect { response -> println("The result is: $response") }
//    println("Request all done!")
//}

/**
 * 转换操作符
 * 使用 transform 操作符，我们可以 发射 任意值任意次。
 */
//fun main() = runBlocking {
//    // asFlow会自动发送数据
//    (1..5).asFlow()
//        .transform { request ->
//            emit("Making request $request")
//            emit(mockRequest(request))
//        }
//        .collect { response -> println("The result is: $response") }
//    println("Request all done!")
//}
/**
 * 限长操作
 */
//fun numbers(): Flow<Int> = flow {
//    try {
//        emit(1)
//        emit(2)
//        println("This line will not execute")
//        emit(3)
//    } finally {
//        println("Finally in numbers")
//    }
//}
//
//fun main() = runBlocking<Unit> {
//    numbers()
//        .take(2) // 只获取前两个
//        .collect { value -> println(value) }
//}

/**
 * 末端流操作
 */
fun main() = runBlocking<Unit> {

    val sum = (1..5).asFlow()
        .map { it * it } // 数字 1 至 5 的平方
        .reduce { a, b -> a + b } // 求和（末端操作符）
    println(sum)
}
