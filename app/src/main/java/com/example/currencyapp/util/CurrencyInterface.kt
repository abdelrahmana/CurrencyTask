package com.example.currencyapp.util

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.currencyapp.data.model.MigrateSymbols
import com.example.currencyapp.ui.currency.adapter.SpinnerCustomAdpter


interface CurrencyInterface {
    fun setTypeSelected(
                        viewContainer : View, selectedItem :(MigrateSymbols)->Unit)

    fun getSelectedItemInPosition():String

    }

class ImplementerFromToCurrency(val adapterView: SpinnerCustomAdpter,val spinner: Spinner, ) : CurrencyInterface {
    override fun setTypeSelected(
        viewContainer: View,
        selectedItem: (MigrateSymbols) -> Unit
    ) {

        spinner.adapter = adapterView
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View
                                        , position: Int, id: Long) {
                // textViewValue?.text = (spinner.selectedItem as Citty).name_by_app_lang?:""
                selectedItem.invoke(adapterView.list[(spinner.selectedItemPosition)])
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
        viewContainer.setOnClickListener{
            spinner.performClick()
        }

    }

    override fun getSelectedItemInPosition(): String {
       return adapterView.list[spinner.selectedItemPosition].key
    }

}

