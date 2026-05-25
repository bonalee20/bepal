package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Stock
import com.example.myapplication.data.repository.StockRepository
import com.example.myapplication.util.StockAlertManager
import com.example.myapplication.util.WorkManagerScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = StockRepository.instance

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
    fun getAppKey() = APP_KEY
    fun getAppSecret() = APP_SECRET
    fun checkAndNotify(context: Context) {
        viewModelScope.launch {
            android.util.Log.d("StockAlert", "checkAndNotify 호출됨, 종목 수: ${_stockList.value.size}")
            StockAlertManager.checkAndNotify(
                context = context,
                stockList = _stockList.value,
                appKey = APP_KEY,
                appSecret = APP_SECRET
            )
        }
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
                _stockList.value = _stockList.value + stock.copy(
                    purchasePrice = purchasePrice,  // 👈 추가
                    quantity = quantity,             // 👈 추가
                    targetRate = targetRate          // 👈 추가
                )
            } catch (e: Exception) {
                android.util.Log.e("AddStock", "조회 실패: ${e.message}", e)
                _errorMessage.value = "현재가 조회 실패: ${e.message}"
                val stock = Stock(
                    stockCode = stockCode,
                    companyName = stockName,
                    price = purchasePrice,
                    changeRate = "0",
                    sign = "3",
                    purchasePrice = purchasePrice,  // 👈 추가
                    quantity = quantity,             // 👈 추가
                    targetRate = targetRate          // 👈 추가
                )
                _stockList.value = _stockList.value + stock
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun startStockCheckSchedule(context: Context) {
        WorkManagerScheduler.scheduleStockCheck(
            context = context,
            stockList = _stockList.value,
            appKey = APP_KEY,
            appSecret = APP_SECRET
        )
    }


}