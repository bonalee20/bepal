package com.example.myapplication.util

import com.example.myapplication.data.model.Stock
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StockSerializer {
    private val gson = Gson()

    fun toJson(stockList: List<Stock>): String {
        return gson.toJson(stockList)
    }

    fun fromJson(json: String): List<Stock> {
        val type = object : TypeToken<List<Stock>>() {}.type
        return gson.fromJson(json, type)
    }
}