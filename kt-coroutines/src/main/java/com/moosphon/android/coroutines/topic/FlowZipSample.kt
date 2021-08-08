package com.moosphon.android.coroutines.topic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 就像 Kotlin 标准库中的 Sequence.zip 扩展函数一样， 流拥有一个 zip 操作符用于组合两个流中的相关值
 */
fun main() = runBlocking {
//    val numberFlow = (1..3).asFlow()
//    val stringFlow = flowOf("one", "two", "three")
//    numberFlow.zip(stringFlow) { number, string ->
//        "$number ->> $string"
//    }.collect{ println(it) }

    // 然而：先前示例中的数字如果每 300 毫秒更新一次，但字符串每 400 毫秒更新一次， 然后使用 zip 操作符合并它们，但仍会产生和上面相同的结果

    val nums = (1..7).asFlow().onEach { delay(100) } // 发射数字 1..3，间隔 300 毫秒
    val strs = flowOf("one", "two", "three", "four", "five").onEach { delay(400) } // 每 400 毫秒发射一次字符串
    val startTime = System.currentTimeMillis() // 记录开始的时间
    nums.zip(strs) { a, b -> "$a -> $b" } // 使用“zip”组合单个字符串
        .collect { value -> // 收集并打印
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}