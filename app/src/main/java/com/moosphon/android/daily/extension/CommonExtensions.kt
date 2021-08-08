package com.moosphon.android.daily.extension

import java.util.*


fun ClosedRange<Char>.randomString(lenght: Int) =
    (1..lenght)
        .map { (Random().nextInt(endInclusive.toInt() - start.toInt()) + start.toInt()).toChar() }
        .joinToString("")

fun getRandomString(length: Int): String {
    return ('a'..'z').randomString(length)
}