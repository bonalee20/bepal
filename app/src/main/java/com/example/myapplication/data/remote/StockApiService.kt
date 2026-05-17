package com.example.myapplication.data.remote

import com.example.myapplication.data.model.StockResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StockApiService {
    @GET("uapi/domestic-stock/v1/quotations/inquire-price")
    suspend fun getStockPrice(
        @Header("authorization") authorization: String,
        @Header("appkey") appKey: String,
        @Header("appsecret") appSecret: String,
        @Header("tr_id") trId: String = "FHKST01010100",
        @Header("custtype") custType: String = "P",
        @Query("FID_COND_MRKT_DIV_CODE") fidCondMrktDivCode: String = "J",
        @Query("FID_INPUT_ISCD") fidInputIscd: String
    ): StockResponse
}