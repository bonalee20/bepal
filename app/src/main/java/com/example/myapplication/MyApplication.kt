package com.example.myapplication

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "0e98bbcb4604cf2cfa8bd545a857d1d4")
    }
}