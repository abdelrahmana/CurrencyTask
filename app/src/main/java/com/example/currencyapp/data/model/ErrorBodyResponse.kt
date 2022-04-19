package com.example.currencyapp.data.model

data class ErrorBodyResponse(
    val error: Error= Error(),
    val success: Boolean = false
)

data class Error(
    val code: Int = 0,
    var info: String=""
)