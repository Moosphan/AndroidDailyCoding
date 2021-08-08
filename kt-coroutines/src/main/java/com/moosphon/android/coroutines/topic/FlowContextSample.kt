package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun sample() = flow {
    log("started sample flow")
    for(i in 1..5) {
        Thread.sleep(100)
        log("Emitting $i")
        emit(i)
    }
}.flowOn(Dispatchers.Default)

/**
 * flowOn 函数，该函数用于更改流发射的上下文。
 * 当上游流必须改变其上下文中的 CoroutineDispatcher 的时候，flowOn 操作符创建了另一个协程。
 */
fun main() = runBlocking {
    sample().collect{
        value -> log("Collected $value")
    }
}