package com.example.myapplication.util

import android.content.Context
import android.util.Log
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

object KakaoMessageSender {

    fun sendTargetReachedMessage(
        context: Context,
        companyName: String,
        currentPrice: String,
        targetPrice: String,
        changeRate: String,
        purchaseSite: String
    ) {
        val template = FeedTemplate(
            content = Content(
                title = "🎯 목표가 달성! $companyName",
                description = "현재가: ${currentPrice}원\n목표가: ${targetPrice}원\n등락률: ${changeRate}%\n\n📌 $purchaseSite",
                imageUrl = "https://via.placeholder.com/300x200",
                link = Link()
            ),
            buttons = listOf(
                Button(
                    title = "비팔톡 열기",
                    link = Link()
                )
            )
        )

        TalkApiClient.instance.sendDefaultMemo(template) { error ->
            if (error != null) {
                Log.e("KakaoMessage", "메시지 전송 실패: ${error.message}")
            } else {
                Log.d("KakaoMessage", "메시지 전송 성공!")
            }
        }
    }
}