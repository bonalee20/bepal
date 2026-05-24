package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Stock
import com.example.myapplication.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = StockRepository()

    private val _stockList = MutableStateFlow<List<Stock>>(emptyList())
    val stockList: StateFlow<List<Stock>> = _stockList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val APP_KEY = "PSXWddRd0igLs7JPEI9aoGMbhqIp4ClAa8ds"
    private val APP_SECRET = "46ni/y8Ys3diaF8Zs2Owj5RWhLDJWTdxn7TZ5r4341SyNoydFIXJ48oWqJ9GryC65uPUmagLPtBAWAFjarTd4rEQrPe4/lzMYzw92u3Zup1x0w+ARWAIi77kkFcfI+pVvn2kfHeKx++6C6o2nJIlCFZ51kO4HlfM/WgXrVhRjNZ/mri3xs8="

    fun fetchStock(stockCode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val stock = repository.getStockPrice(APP_KEY, APP_SECRET, stockCode)
                _stockList.value = _stockList.value + stock
            } catch (e: Exception) {
                _errorMessage.value = "오류: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadInitialStocks() {

    }
    fun addStock(
        stockCode: String,
        stockName: String,
        purchasePrice: String,
        quantity: String,
        targetRate: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                android.util.Log.d("AddStock", "stockCode: $stockCode 조회 시작")
                val stock = repository.getStockPrice(APP_KEY, APP_SECRET, stockCode)
                android.util.Log.d("AddStock", "조회 성공: ${stock.companyName}, ${stock.price}")
                _stockList.value = _stockList.value + stock
            } catch (e: Exception) {
                android.util.Log.e("AddStock", "조회 실패: ${e.message}", e)
                _errorMessage.value = "현재가 조회 실패: ${e.message}"
                // fallback
                val stock = Stock(
                    stockCode = stockCode,
                    companyName = stockName,
                    price = purchasePrice,
                    changeRate = targetRate,
                    sign = "3"
                )
                _stockList.value = _stockList.value + stock
            } finally {
                _isLoading.value = false
            }
        }
    }
}