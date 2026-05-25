package com.example.myapplication.util

import android.content.Context
import androidx.work.*
import com.example.myapplication.data.model.Stock
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {

    fun scheduleStockCheck(
        context: Context,
        stockList: List<Stock>,
        appKey: String,
        appSecret: String
    ) {
        val inputData = workDataOf(
            "stock_list" to StockSerializer.toJson(stockList),
            "app_key" to appKey,
            "app_secret" to appSecret
        )

        val request = PeriodicWorkRequestBuilder<StockCheckWorker>(
            15, TimeUnit.MINUTES  // 최소 주기 15분
        )
            .setInputData(inputData)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "stock_check",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelStockCheck(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("stock_check")
    }
}