package com.example.myapplication.ui.add.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.myapplication.data.model.StockSearchResult
import com.example.myapplication.data.repository.StockSearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StockSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onStockSelected: (stockCode: String, stockName: String) -> Unit
) {
    val repository = remember { StockSearchRepository() }
    val coroutineScope = rememberCoroutineScope()
    var suggestions by remember { mutableStateOf<List<StockSearchResult>>(emptyList()) }
    var showDropdown by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = { input ->
                onValueChange(input)
                if (input.length >= 2) {
                    isLoading = true
                    coroutineScope.launch {
                        delay(300L)
                        suggestions = repository.searchStock(input)
                        isLoading = false
                        showDropdown = suggestions.isNotEmpty()
                    }
                } else {
                    suggestions = emptyList()
                    showDropdown = false
                }
            },
            placeholder = { Text("삼성전자", color = Color.LightGray) },
            trailingIcon = {
                if (isLoading) CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color(0xFFFF802D),
                    strokeWidth = 2.dp
                )
            },
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
                suggestions.forEach { stock ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onStockSelected(stock.stockCode, stock.stockName)
                                onValueChange(stock.stockName)
                                showDropdown = false
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stock.stockName, fontSize = 14.sp)
                        Text(stock.stockCode, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}