package com.example.myapplication.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication.data.repository.StockRepository

class StockCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val stockListJson = inputData.getString("stock_list") ?: return Result.failure()
            val appKey = inputData.getString("app_key") ?: return Result.failure()
            val appSecret = inputData.getString("app_secret") ?: return Result.failure()

            val stockList = StockSerializer.fromJson(stockListJson)

            StockAlertManager.checkAndNotify(
                context = context,
                stockList = stockList,
                appKey = appKey,
                appSecret = appSecret
            )

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}