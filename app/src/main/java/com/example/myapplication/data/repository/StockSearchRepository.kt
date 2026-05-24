package com.example.myapplication.data.repository

import android.util.Log

import com.example.myapplication.data.model.StockSearchResult
import com.example.myapplication.data.remote.StockSearchApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class StockSearchRepository {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val req = chain.request()
                    val response = chain.proceed(req)
                    val body = response.peekBody(Long.MAX_VALUE)
                    response
                }
                .build()
        )
        .build()
    private val api = retrofit.create(StockSearchApiService::class.java)

    private val SERVICE_KEY = "072271224e997cbef3a7b40c25b78c2272f64f273433ea58b24cde9f8875caed"




    suspend fun searchStock(query: String): List<StockSearchResult> {
        if (query.isBlank()) return emptyList()
        return try {
            Log.d("StockSearch", "검색 시작: query=$query, take2=${query.take(2)}")

            val response = api.searchStock(
                serviceKey = SERVICE_KEY,
                itmsNm = query,
                numOfRows = 50
            )

            Log.d("StockSearch", "응답 totalCount=${response.response.body.totalCount}")
            Log.d("StockSearch", "응답 items=${response.response.body.items.item.size}개")
            response.response.body.items.item.forEach {
                Log.d("StockSearch", "종목: ${it.itmsNm} / ${it.srtnCd}")
            }

            val filtered = response.response.body.items.item
                .filter { it.itmsNm.contains(query, ignoreCase = true) }
                .map {
                    StockSearchResult(
                        stockCode = it.srtnCd.removePrefix("A"),  // A005930 → 005930
                        stockName = it.itmsNm
                    )
                }
                .distinctBy { it.stockCode }

            Log.d("StockSearch", "필터링 후: ${filtered.size}개")
            filtered

        } catch (e: Exception) {
            Log.e("StockSearch", "오류 발생: ${e.message}", e)
            emptyList()
        }
    }
}