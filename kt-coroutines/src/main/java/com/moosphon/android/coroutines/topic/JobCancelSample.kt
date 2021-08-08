package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("Job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1400L)
    println("main: I wanna cancel!")
    //job.cancelAndJoin()
    job.cancel()
    job.join()
    println("main: I can quit now.")

    /**
     * finally: 一般在协程被取消的时候执行它们的终结动作
     */
    val otherJob = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")
    otherJob.cancelAndJoin() // 取消该作业并且等待它结束
    println("main: Now I can quit.")
}