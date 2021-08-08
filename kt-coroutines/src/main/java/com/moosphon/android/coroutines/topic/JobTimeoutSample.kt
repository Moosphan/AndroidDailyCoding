package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

/**
 * 取消一个协程的理由是它有可能超时。 当你手动追踪一个相关 Job 的引用并启动了一个单独的协程在延迟后取消追踪
 */
fun main() = runBlocking {
//    val result = withTimeout(2000) {
//        repeat(10) {
//            println("I'm sleeping $it ...")
//            delay(500L)
//        }
//        "Done" // 结果
//    }
    val result = withTimeoutOrNull(2000) {
        repeat(10) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
        "Done" // 结果
    }
    println("The result is: $result")
}