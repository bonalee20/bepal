package com.example.myapplication.data.model

data class Stock(
    val stockCode: String,
    val companyName: String,
    val price: String,
    val changeRate: String,
    val sign: String,
    val purchasePrice: String = "0",
    val quantity: String = "0",
    val targetRate: String = "0",
    val highPrice: String = "0"
)