package com.example.currencyapp.di

import android.content.Context
import com.example.currencyapp.data.CurrencyDataSource
import com.example.currencyapp.data.repo.CurrencyRepo
import com.example.currencyapp.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class, FragmentComponent::class,
    ActivityComponent::class)
class RepoDi {
    @Provides
    fun getCurrencyRepo(webService: CurrencyDataSource,util : Util,@ApplicationContext context: Context
    ): CurrencyRepo {
        return  CurrencyRepo(webService,util,context)
    }

}