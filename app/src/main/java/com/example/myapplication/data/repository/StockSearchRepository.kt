package com.example.myapplication.data.repository

import com.example.myapplication.data.model.StockSearchResult
import com.example.myapplication.data.remote.StockSearchApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockSearchRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(StockSearchApiService::class.java)

    private val SERVICE_KEY = "072271224e997cbef3a7b40c25b78c2272f64f273433ea58b24cde9f8875caed"

    suspend fun searchStock(query: String): List<StockSearchResult> {
        if (query.isBlank()) return emptyList()
        return try {
            val response = api.searchStock(
                serviceKey = SERVICE_KEY,
                itmsNm = query
            )
            response.response.body.items.item.map {
                StockSearchResult(
                    stockCode = it.srtnCd,
                    stockName = it.itmsNm
                )
            }.distinctBy { it.stockCode }
        } catch (e: Exception) {
            emptyList()
        }
    }
}