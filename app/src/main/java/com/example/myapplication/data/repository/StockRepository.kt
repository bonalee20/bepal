package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Stock
import com.example.myapplication.data.remote.StockApiService
import com.example.myapplication.data.remote.TokenApiService
import com.example.myapplication.data.remote.TokenRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockRepository private constructor() {

    companion object {
        val instance: StockRepository by lazy { StockRepository() }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.koreainvestment.com:9443/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(StockApiService::class.java)
    private val tokenApi = retrofit.create(TokenApiService::class.java)

    private var cachedToken: String? = null
    private var tokenExpiredAt: Long = 0

    private suspend fun fetchAccessToken(appKey: String, appSecret: String): String {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiredAt) {
            return cachedToken!!
        }
        val response = tokenApi.getAccessToken(
            TokenRequest(appKey = appKey, appSecret = appSecret)
        )
        cachedToken = response.accessToken
        tokenExpiredAt = System.currentTimeMillis() + (6 * 60 * 60 * 1000)
        return response.accessToken
    }

    suspend fun getStockPrice(
        appKey: String,
        appSecret: String,
        stockCode: String
    ): Stock {
        val cleanCode = stockCode.removePrefix("A")
        return try {
            val token = fetchAccessToken(appKey, appSecret)
            requestStockPrice(token, appKey, appSecret, cleanCode)
        } catch (e: Exception) {
            if (e.message?.contains("403") == true) {
                cachedToken = null
                tokenExpiredAt = 0
                val newToken = fetchAccessToken(appKey, appSecret)
                requestStockPrice(newToken, appKey, appSecret, cleanCode)
            } else {
                throw e
            }
        }
    }

    private suspend fun requestStockPrice(
        token: String,
        appKey: String,
        appSecret: String,
        cleanCode: String
    ): Stock {
        val auth = "Bearer $token"
        val priceResponse = api.getStockPrice(
            authorization = auth,
            appKey = appKey,
            appSecret = appSecret,
            fidInputIscd = cleanCode
        )
        val infoResponse = api.getStockInfo(
            authorization = auth,
            appKey = appKey,
            appSecret = appSecret,
            pdno = cleanCode
        )
        return Stock(
            stockCode = cleanCode,
            companyName = infoResponse.output?.companyName ?: cleanCode,
            price = priceResponse.output.price ?: "0",
            changeRate = priceResponse.output.changeRate ?: "0",
            sign = priceResponse.output.sign ?: "3",
            highPrice = priceResponse.output.highPrice ?: "0"
        )
    }
}
