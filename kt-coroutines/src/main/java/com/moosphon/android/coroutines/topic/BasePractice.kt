package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.*

fun main() {
    val job = GlobalScope.launch {
        delay(1000L) // 非阻塞等待1s
        println("World!")
    }
    print("Hello, ")
    blockThreadDelay(2000) // 阻塞主线程2s确保jvm存活

    // 作用域内执行，结构化并发
    runBlocking {
        val job = launch {
            delay(1000L)
            print("World!")
        }
        print("Hello, ")
        job.join()
    }
}

/**
 * 阻塞主线程
 */
fun blockThreadDelay(delay: Long) {
    // 1. 直接操作线程
    //Thread.sleep(delay)
    // 2. 使用协程构建器阻塞(作用域)
    runBlocking {
        delay(delay)
    }
}