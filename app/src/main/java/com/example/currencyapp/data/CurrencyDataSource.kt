package com.example.currencyapp.data

import com.example.currencyapp.data.model.ConvertResponse
import com.example.currencyapp.data.model.SymbolesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CurrencyDataSource {
    /*  & from = GBP
    & to = JPY
    & amount = 25*/
    @GET("convert")
    suspend fun getFromToCurrency(@QueryMap hashMap: HashMap<String,Any>): ApiResponse<ConvertResponse>
    @GET("symbols")
    suspend fun getSymbolsCurrencies(): ApiResponse<SymbolesResponse>

}