package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    // 1.默认顺序调用(同步)
    //doSyncTasks()
    // 2.并发调用
    //doAsyncTasks()
    // 3.并发惰性调用
    //doAsyncTasksByLazy()
    // 4.async风格函数
    doAsyncTasksWithoutSuspend()
}

fun doSyncTasks() {
    runBlocking {
        val time = measureTimeMillis {
            val taskAResult = doTaskA()
            val taskBResult = doTaskB()
            println("Task A result is: $taskAResult, task B result is: $taskBResult")
        }
        println("Completed in $time ms")
    }
}

/**
 * 在概念上，async 就类似于 launch。
 * 它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。
 * 不同之处在于 launch 返回一个 Job 并且不附带任何结果值，而 async 返回一个 Deferred —— 一个轻量级的非阻塞 future，
 * 这代表了一个将会在稍后提供结果的 promise。你可以使用 .await() 在一个延期的值上得到它的最终结果， 但是 Deferred 也是一个 Job，所以如果需要的话，你可以取消它。
 */
fun doAsyncTasks() {
    runBlocking {
        val time = measureTimeMillis {
            val taskAResult = async { doTaskA() }
            val taskBResult = async { doTaskB() }
            println("Task A result is: ${taskAResult.await()}, task B result is: ${taskBResult.await()}")
        }
        println("Completed in $time ms")
    }
}

/**
 * 惰性启动
 * 只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候。
 */
fun doAsyncTasksByLazy() {
    runBlocking {
        val time = measureTimeMillis {
            val taskA = async(start = CoroutineStart.LAZY) { doTaskA() }
            val taskB = async(start = CoroutineStart.LAZY) { doTaskB() }
            taskA.start()
            taskB.start()
            println("Task A result is: ${taskA.await()}, task B result is: ${taskB.await()}")
        }
        println("Completed in $time ms")
    }
}

fun doAsyncTasksWithoutSuspend() {
    val time = measureTimeMillis {
        val one = emitTaskA()
        val two = emitTaskB()
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

/**
 * 结构化并发
 */
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了些有用的事
    return 29
}

fun emitTaskA() = GlobalScope.async {
    doTaskA()
}

fun emitTaskB() = GlobalScope.async {
    doTaskB()
}

suspend fun doTaskA(): String {
    delay(1000L)
    return "Task A done!"
}

suspend fun doTaskB(): String {
    delay(1000L)
    return "Task B done!"
}