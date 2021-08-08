package com.moosphon.android.kt.demo

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// 1. 属性委托(by map)
open class User(map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

val user = User(mapOf(
    "name" to "John Doe",
    "age"  to 25
))

// 2. 属性委托(by observable)
class Student(val map: Map<String, Any?>): User(map) {
    var grade: Int by Delegates.observable(1) {
        property, oldValue, newValue ->
        println("the student $name is upgrade to $newValue")
    }
}

// 3. 属性委托(by lazy)
private val str: String by lazy {
    println("(=・ω・=)")
    "hello world"
}

// 4. 自定义委托(封装sp、页面参数传递或者自动findViewById等)
class LocalUser {
    var username by stringValue()
    var userAccount by intValue()
}
// 仅用于模拟功能
var activity: Activity = Activity()

fun stringValue(default: String = "") = object : ReadWriteProperty<Any, String?> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        sp.edit().putString(property.name, value).apply()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return sp.getString(property.name, default)
    }

}

fun intValue(default: Int = 0) = object : ReadWriteProperty<Any, Int> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        sp.edit().putInt(property.name, value).apply()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return sp.getInt(property.name, default)
    }
}

private val sp: SharedPreferences by lazy {
    activity.getSharedPreferences("test", Context.MODE_PRIVATE)
}
