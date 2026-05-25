package com.example.myapplication.util

import android.content.Context
import android.util.Log
import com.example.myapplication.data.model.Stock
import com.example.myapplication.data.repository.StockRepository

object StockAlertManager {

    private val repository = StockRepository()

    suspend fun checkAndNotify(
        context: Context,
        stockList: List<Stock>,
        appKey: String,
        appSecret: String
    ) {
        stockList.forEach { stock ->
            try {
                val current = repository.getStockPrice(appKey, appSecret, stock.stockCode)
                val currentPrice = current.price.toDoubleOrNull() ?: return@forEach
                val purchasePrice = stock.purchasePrice.toDoubleOrNull() ?: return@forEach
                val targetRate = stock.targetRate.toDoubleOrNull() ?: return@forEach
                val highPrice = current.highPrice?.toDoubleOrNull() ?: currentPrice

                val targetPrice = purchasePrice * (1 + targetRate / 100)

                Log.d("StockAlert", "${stock.companyName} 현재가: $currentPrice 목표가: $targetPrice")

                if (highPrice >= targetPrice || currentPrice >= targetPrice) {
                    KakaoMessageSender.sendTargetReachedMessage(
                        context = context,
                        companyName = stock.companyName,
                        currentPrice = "%,.0f".format(currentPrice),
                        targetPrice = "%,.0f".format(targetPrice),
                        changeRate = current.changeRate
                    )
                }

            } catch (e: Exception) {
                Log.e("StockAlert", "${stock.companyName} 오류: ${e.message}")
            }
        }
    }
}