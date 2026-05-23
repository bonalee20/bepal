package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Stock
import com.example.myapplication.data.remote.StockApiService
import com.example.myapplication.data.remote.TokenApiService
import com.example.myapplication.data.remote.TokenRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.koreainvestment.com:9443/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(StockApiService::class.java)
    private val tokenApi = retrofit.create(TokenApiService::class.java)

    private var cachedToken: String? = null

    private suspend fun fetchAccessToken(appKey: String, appSecret: String): String {
        cachedToken?.let { return it }
        val response = tokenApi.getAccessToken(
            TokenRequest(appKey = appKey, appSecret = appSecret)
        )
        cachedToken = response.accessToken
        return response.accessToken
    }

    suspend fun getStockPrice(
        appKey: String,
        appSecret: String,
        stockCode: String
    ): Stock {
        val token = fetchAccessToken(appKey, appSecret)
        val auth = "Bearer $token"

        val priceResponse = api.getStockPrice(
            authorization = auth,
            appKey = appKey,
            appSecret = appSecret,
            fidInputIscd = stockCode
        )

        val infoResponse = api.getStockInfo(
            authorization = auth,
            appKey = appKey,
            appSecret = appSecret,
            pdno = stockCode
        )

        android.util.Log.d("StockAPI", "companyName: ${infoResponse.output?.companyName}")

        return Stock(
            stockCode = priceResponse.output.stockCode ?: stockCode,
            companyName = infoResponse.output?.companyName ?: stockCode,
            price = priceResponse.output.price ?: "0",
            changeRate = priceResponse.output.changeRate ?: "0",
            sign = priceResponse.output.sign ?: "3"
        )
    }

}