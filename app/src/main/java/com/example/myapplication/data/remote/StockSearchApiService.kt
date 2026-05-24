package com.example.myapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class KrxResponse(
    val response: KrxResponseBody
)

data class KrxResponseBody(
    val header: KrxHeader,
    val body: KrxResponseItems
)

data class KrxHeader(
    val resultCode: String,
    val resultMsg: String
)

data class KrxResponseItems(
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
    val items: KrxItemList
)

data class KrxItemList(
    val item: List<KrxStockItem>
)

data class KrxStockItem(
    val basDt: String,
    val srtnCd: String,
    val isinCd: String,
    val mrktCtg: String,
    val itmsNm: String,
    val crno: String,
    val corpNm: String
)

interface StockSearchApiService {
    @GET("1160100/service/GetKrxListedInfoService/getItemInfo")
    suspend fun searchStock(
        @Query("serviceKey", encoded = true) serviceKey: String,  // encoded = true 추가
        @Query("numOfRows") numOfRows: Int = 50,
        @Query("pageNo") pageNo: Int = 1,
        @Query("resultType") resultType: String = "json",
        @Query("itmsNm") itmsNm: String
    ): KrxResponse
}