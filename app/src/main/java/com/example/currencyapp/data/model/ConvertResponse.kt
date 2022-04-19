package com.example.currencyapp.data.model

data class ConvertResponse(
    val date: String,
    val historical: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean,
    val error : Error
    )

data class Info(
    val rate: Double,
    val timestamp: Int
)

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)