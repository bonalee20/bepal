package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class StockResponse(
    @SerializedName("output") val output: StockOutput,
    @SerializedName("rt_cd") val rtCd: String,
    @SerializedName("msg1") val msg1: String
)

data class StockOutput(
    @SerializedName("stck_shrn_iscd") val stockCode: String?,
    @SerializedName("hts_kor_isnm") val companyName: String?,
    @SerializedName("stck_prpr") val price: String?,
    @SerializedName("prdy_ctrt") val changeRate: String?,
    @SerializedName("prdy_vrss_sign") val sign: String?
)