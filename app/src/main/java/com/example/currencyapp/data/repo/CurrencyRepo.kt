package com.example.currencyapp.data.repo

import android.content.Context
import com.example.currencyapp.data.CurrencyDataSource
import com.example.currencyapp.data.model.Symbols
import com.example.currencyapp.util.Util
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CurrencyRepo @Inject constructor(private val webService: CurrencyDataSource,
                                       private val util : Util, @ApplicationContext val context: Context) {

    suspend fun getSymbolsList(completion: (Symbols?, String?) -> Unit) {

        val res =  webService.getSymbolsCurrencies()
        res.onSuccess {
            if (data?.success==false)
                completion(null , data?.error?.info?:"")

            completion(data?.symbols ,null)

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,util.getErrorBodyResponse(errorBody)) // handle error from error body
        }
    }
    suspend fun getInfoFromTo(hashMap: HashMap<String, Any>, completion: (String?, String?) -> Unit) {

        val res =  webService.getFromToCurrency(hashMap)
        res.onSuccess {
            if (data?.success==false)
                completion(null , data?.error?.info?:"")

            completion(data?.result.toString() ,null)

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,util.getErrorBodyResponse(errorBody)) // handle error from error body
        }
    }

}