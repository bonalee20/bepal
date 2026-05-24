package com.example.myapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class KrxStockItem(
    val srtnCd: String,      // 단축코드 (종목코드)
    val itmsNm: String,      // 종목명
    val mrktCtg: String      // 시장구분 (KOSPI/KOSDAQ)
)

data class KrxResponse(
    val response: KrxResponseBody
)

data class KrxResponseBody(
    val body: KrxResponseItems
)

data class KrxResponseItems(
    val items: KrxItemList,
    val totalCount: Int
)

data class KrxItemList(
    val item: List<KrxStockItem>
)

interface StockSearchApiService {
    @GET("1160100/service/GetKrxListedInfoService/getItemInfo")
    suspend fun searchStock(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int = 20,
        @Query("pageNo") pageNo: Int = 1,
        @Query("resultType") resultType: String = "json",
        @Query("itmsNm") itmsNm: String  // 종목명으로 검색
    ): KrxResponse
}