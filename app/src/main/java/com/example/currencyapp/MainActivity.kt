package com.example.currencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyapp.databinding.ActivityMainBinding
import com.example.currencyapp.ui.currency.CurrencyFragment
import com.example.currencyapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var util: Util
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        util.changeFragmentBack(this, CurrencyFragment(), CURRENCY, null, R.id.fragment_container)
    }

    companion object {
        const val CURRENCY = "CURRENCY_FRAGMENT"
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

    }
}