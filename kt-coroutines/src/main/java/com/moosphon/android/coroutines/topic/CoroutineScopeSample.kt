package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.*

/**
 * 我们通过创建一个 CoroutineScope 实例来管理协程的生命周期，并使它与 activity 的生命周期相关联。
 * CoroutineScope 可以通过 CoroutineScope() 创建或者通过MainScope() 工厂函数。
 * 前者创建了一个通用作用域，而后者为使用 Dispatchers.Main 作为默认调度器的 UI 应用程序 创建作用域.
 */
class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default) // use Default for test purposes

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        // 在示例中启动了 10 个协程，且每个都工作了不同的时长
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒等等不同的时间
                println("Coroutine $i is done")
            }
        }
    }
} // Activity 类结束

fun main() = runBlocking<Unit> {
    val activity = Activity()
    activity.doSomething() // 运行测试函数
    println("Launched coroutines")
    delay(500L) // 延迟半秒钟
    println("Destroying activity!")
    activity.destroy() // 取消所有的协程
    delay(1000) // 为了在视觉上确认它们没有工作
}