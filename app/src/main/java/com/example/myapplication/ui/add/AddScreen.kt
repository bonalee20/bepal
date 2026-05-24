package com.example.myapplication.ui.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.myapplication.ui.register.calculateExpectedProfit

@Composable
fun AddScreen(
    onBackClick: () -> Unit = {}
) {
    var stockName by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var purchaseSite by remember { mutableStateOf("") }
    var targetRate by remember { mutableStateOf("") }
    var alarmEnabled by remember { mutableStateOf(false) }

    val expectedProfit = remember(purchasePrice, quantity, targetRate) {
        calculateExpectedProfit(purchasePrice, quantity, targetRate)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 80.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("싸게 사고 비싸게 팔자", fontSize = 12.sp, color = Color.Gray)
            Text(
                "비팔톡",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF802D)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text("종목 등록", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            AddField(label = "구매 종목") {
                StockSearchField(
                    value = stockName,
                    onValueChange = { stockName = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "구매 가격") {
                AddTextField(
                    value = purchasePrice,
                    onValueChange = { purchasePrice = it },
                    placeholder = "400,000",
                    keyboardType = KeyboardType.Number
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "수량(주)") {
                AddTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    placeholder = "10",
                    keyboardType = KeyboardType.Number
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "구매 사이트") {
                AddTextField(
                    value = purchaseSite,
                    onValueChange = { purchaseSite = it },
                    placeholder = "토스 증권"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "목표 수익률 (%)") {
                AddTextField(
                    value = targetRate,
                    onValueChange = { targetRate = it },
                    placeholder = "10",
                    keyboardType = KeyboardType.Number
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "예상 수익") {
                AddTextField(
                    value = expectedProfit,
                    onValueChange = {},
                    placeholder = "440,0000",
                    readOnly = true
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("알람 동의", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(
                    checked = alarmEnabled,
                    onCheckedChange = { alarmEnabled = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF802D))
                )
            }
            Text(
                "설정하신 수신 수단으로 목표 수익률에 도달했을 때 알람 발송",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: 저장 로직 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF802D))
            ) {
                Text("등록", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedTab = "종목"
        )
    }
}

@Composable
fun AddField(label: String, content: @Composable () -> Unit) {
    Column {
        Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        content()
    }
}

@Composable
fun AddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.LightGray) },
        modifier = Modifier.fillMaxWidth(),
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF802D),
            unfocusedBorderColor = Color.LightGray
        ),
        singleLine = true
    )
}

@Composable
fun StockSearchField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val suggestions = listOf("삼성전자", "삼성SDI", "삼성바이오로직스", "삼성물산")
        .filter { it.contains(value) && value.isNotEmpty() }
    var showDropdown by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showDropdown = it.isNotEmpty()
            },
            placeholder = { Text("삼성전자", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF802D),
                unfocusedBorderColor = Color.LightGray
            ),
            singleLine = true
        )

        if (showDropdown && suggestions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp)
                    .zIndex(1f)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            ) {
                suggestions.forEach { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onValueChange(suggestion)
                                showDropdown = false
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedTab: String = "홈"
) {
    val tabs = listOf("홈", "종목", "수익", "설정")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(0.5.dp, Color.LightGray),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Text(
                text = tab,
                modifier = Modifier.padding(vertical = 14.dp),
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color(0xFFFF802D) else Color.Gray
            )
        }
    }
}