package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class StockInfoResponse(
    @SerializedName("output") val output: StockInfoOutput?,
    @SerializedName("rt_cd") val rtCd: String,
    @SerializedName("msg1") val msg1: String
)

data class StockInfoOutput(
    @SerializedName("prdt_abrv_name") val companyName: String?  // 종목명
)