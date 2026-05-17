package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Stock
import com.example.myapplication.data.model.StockOutput
import com.example.myapplication.data.remote.StockApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockRepository {

    private val api: StockApiService = Retrofit.Builder()
        .baseUrl("https://openapi.koreainvestment.com:9443/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StockApiService::class.java)

    suspend fun getStockPrice(
        appKey: String,
        appSecret: String,
        accessToken: String,
        stockCode: String
    ): Stock {
        val response = api.getStockPrice(
            authorization = "Bearer $accessToken",
            appKey = appKey,
            appSecret = appSecret,
            trId = "FHKST01010100",
            custType = "P",
            fidInputIscd = stockCode
        )
        return Stock(
            stockCode = response.output.stockCode,
            companyName = response.output.companyName,
            price = response.output.price,
            changeRate = response.output.changeRate,
            sign = response.output.sign
        )
    }
}