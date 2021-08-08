package com.moosphon.android.daily.extension

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty

/**
 * Provide extensions of property delegates for obtaining arguments in [Activity] and [Fragment].
 * @author Moosphon
 * @date 2021/07/26
 */

fun <T> Fragment.argument(defaultValue: T? = null) = FragmentArgumentProperty(defaultValue)

fun <T> Activity.argumentNullable() = ActivityArgumentDelegateNullable<T>()

fun <T> Activity.argument(defaultValue: T? = null) = ActivityArgumentProperty(defaultValue)

class FragmentArgumentProperty<T>(private val defaultValue: T? = null): ReadWriteProperty<Fragment, T> {
    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val arguments = thisRef.arguments ?: Bundle().also { thisRef.arguments = it }
        arguments[property.name] = value
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.getValue(property.name) as? T
            ?: defaultValue
            ?: throw IllegalStateException("Property ${property.name} could not be found")
    }
}

class ActivityArgumentProperty<T>(private val defaultValue: T? = null) :
    ReadOnlyProperty<Activity, T> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return thisRef.intent?.extras?.getValue(property.name) as? T
            ?: defaultValue
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }
}

class ActivityArgumentDelegateNullable<T> : ReadOnlyProperty<Activity, T?> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T? {
        return thisRef.intent?.extras?.getValue(property.name)
    }
}

operator fun <T> Bundle.set(key: String, value: T?) {
    when(value) {
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Short -> putShort(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is String? -> putString(key, value)
        is CharSequence? -> putCharSequence(key, value)
        is Serializable? -> putSerializable(key, value)
        is Parcelable? -> putParcelable(key, value)
        is Bundle? -> putBundle(key, value)
        is BooleanArray? -> putBooleanArray(key, value)
        is ByteArray? -> putByteArray(key, value)
        is CharArray? -> putCharArray(key, value)
        is ShortArray? -> putShortArray(key, value)
        is IntArray? -> putIntArray(key, value)
        is LongArray? -> putLongArray(key, value)
        is FloatArray? -> putFloatArray(key, value)
        is DoubleArray? -> putDoubleArray(key, value)
        is ArrayList<*>? -> throw IllegalStateException("ArrayList<*> $key is not supported")
        is Array<*>? -> throw IllegalStateException("Array<*> $key is not supported")
        else -> throw IllegalStateException("Type $key is not supported")
    }
}

fun <T> Bundle.getValue(key: String): T? = get(key) as T?