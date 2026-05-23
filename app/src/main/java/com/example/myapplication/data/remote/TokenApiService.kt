package com.example.myapplication.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

data class TokenRequest(
    @SerializedName("grant_type") val grantType: String = "client_credentials",
    @SerializedName("appkey") val appKey: String,
    @SerializedName("appsecret") val appSecret: String
)

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String
)

interface TokenApiService {
    @POST("oauth2/tokenP")
    suspend fun getAccessToken(@Body request: TokenRequest): TokenResponse
}