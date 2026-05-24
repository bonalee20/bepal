package com.example.myapplication.util

import java.text.NumberFormat
import java.util.Locale

fun formatNumber(value: String): String {
    val number = value.replace(",", "").toDoubleOrNull() ?: return value
    return NumberFormat.getNumberInstance(Locale.KOREA).format(number)
}