package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun loginWithKakao(context: Context) {
        val scopes = listOf("talk_message")

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(
                context,
                serviceTerms = scopes
            ) { token, error ->
                if (error != null) {
                    _loginState.value = LoginState.Error(error.message ?: "로그인 실패")
                } else if (token != null) {
                    fetchUserInfo()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                context,
                serviceTerms = scopes
            ) { token, error ->
                if (error != null) {
                    _loginState.value = LoginState.Error(error.message ?: "로그인 실패")
                } else if (token != null) {
                    fetchUserInfo()
                }
            }
        }
    }

    private fun fetchUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (user != null) {
                _loginState.value = LoginState.Success(
                    nickname = user.kakaoAccount?.profile?.nickname ?: "",
                )
            } else {
                _loginState.value = LoginState.Error("유저 정보 조회 실패")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    data class Success(val nickname: String) : LoginState()
    data class Error(val message: String) : LoginState()
}