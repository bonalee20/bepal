package com.example.myapplication.data.model

data class Stock(
    val stockCode: String,      // stck_shrn_iscd
    val companyName: String,    // hts_kor_isnm
    val price: String,          // stck_prpr
    val changeRate: String,     // prdy_ctrt
    val sign: String            // prdy_vrss_sign (1,2=상승 / 4,5=하락)
)