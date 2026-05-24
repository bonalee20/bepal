package com.example.myapplication.ui.register

fun calculateExpectedProfit(
    purchasePrice: String,
    quantity: String,
    targetRate: String
): String {
    val price = purchasePrice.replace(",", "").toLongOrNull() ?: return ""
    val qty = quantity.toLongOrNull() ?: return ""
    val rate = targetRate.toDoubleOrNull() ?: return ""

    val total = price * qty
    val target = total * (1 + rate / 100.0)

    return if (target > 0) "%,.0f".format(target) else ""
}