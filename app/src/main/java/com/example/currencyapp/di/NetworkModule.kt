package com.example.currencyapp.di

import android.content.Context
import android.os.Build
import androidx.core.os.BuildCompat
import com.example.currencyapp.data.CurrencyDataSource
import com.example.currencyapp.di.NetworkModule.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intuit.sdp.BuildConfig
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
// this class to use custom retrofit buildr with differnt cases
 @Module
 @InstallIn(ViewModelComponent::class, FragmentComponent::class,ServiceComponent::class)
class NetworkModule {
    var gson: Gson? = null
        get() {
            if (field == null) {
                field = GsonBuilder().setLenient().create()
            }
            return field
        }
        private set
    private var retrofit: Retrofit? = null
    private var okHttpAuthClient: OkHttpClient? = null
    private var okHttpLocalClient: OkHttpClient? = null


     @Provides
     @ViewModelScoped
     fun getRetrofitCall(@ApplicationContext context: Context?) : CurrencyDataSource {
         return getRetrofit(getHttpAuthClient(context!!),BASE_URL).create(
             CurrencyDataSource::class.java
         )
     }



    val loggingInterceptor: HttpLoggingInterceptor
        get() = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    @Provides
    fun getAuthInterceptor(context: Context?): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val url: HttpUrl =
                original.url.newBuilder().addQueryParameter(ACCESSKEY,
                  ACCESSVALUE).build() // add query in request
            chain.proceed(
                original.newBuilder().url(url)
                    .header("Accept","application/json")
                     .method(original.method, original.body)
                     .build()
            )
        }
    }



    @Provides
    fun getHttpAuthClient(context: Context): OkHttpClient {
        if (okHttpAuthClient == null) {
            okHttpAuthClient = OkHttpClient.Builder()
                .followRedirects(false)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(getAuthInterceptor(context))
              //  .addInterceptor(getCustomErrorInterceptor(context))
                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
        return okHttpAuthClient!!
    }

    fun getRetrofit(client: OkHttpClient?, BASE_URL: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(/*if (BuildConfig.DEBUG)*/ BASE_URL /*else LIVE_BASE_URL*/)
                .addConverterFactory(GsonConverterFactory.create(gson!!))
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
                .client(client!!)


                .build()
        }
        return retrofit!!
    }

    companion object {

      val  NETWORK_TIMEOUT :Long = 30
            var BASE_URL = com.example.currencyapp.BuildConfig.baseUrl
      private  const val ACCESSKEY = "access_key"
     private  val ACCESSVALUE = "f52e3034136058549448054c5296f97c"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AmazonRetrofit

}