package com.mashup.mvvm.utils

import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_DATE_YYYY_MM_DD = "yyyy.MM.dd"

fun Date.toDateString(format: String = FORMAT_DATE_YYYY_MM_DD): String {
    return  SimpleDateFormat(format, Locale.KOREA).format(this)
}