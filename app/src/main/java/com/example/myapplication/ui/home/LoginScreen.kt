package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.LoginState
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            // 로그인 성공 후 talk_message scope 요청
            UserApiClient.instance.loginWithNewScopes(
                context,
                scopes = listOf("talk_message")
            ) { token, error ->
                // 동의 여부 상관없이 홈으로 이동
                onLoginSuccess((loginState as LoginState.Success).nickname)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "싸게 사고 비싸게 팔자",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "비팔톡",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9500)
            )
        }

        Button(
            onClick = { viewModel.loginWithKakao(context) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFEE500)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "카카오톡으로 로그인",
                color = Color(0xFF191919),
                fontWeight = FontWeight.Bold
            )
        }
    }
}