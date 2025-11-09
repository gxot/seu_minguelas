package com.example.seuminguelas.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toReais(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}