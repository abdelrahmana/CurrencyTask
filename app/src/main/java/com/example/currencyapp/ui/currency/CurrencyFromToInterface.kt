package com.example.currencyapp.ui.currency

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.example.currencyapp.util.CurrencyInterface

interface CurrencyFromToInterface {
  //  fun setCalculation(orderTotal : Double, valueAmount : String) : String
    fun getWatcherFromTo(listenerEditText: EditText
    ):TextWatcher
    fun getCurrentEditText(): EditText
}

class CurrencyFromImplementer(val viewModel: CurrencyViewModel,
                              val spinnerCurrencyFrom: CurrencyInterface,
                              val spinnerCurrencyTo : CurrencyInterface,
                              val currentEditTextt :EditText
) :CurrencyFromToInterface{

    var watcherDiscount : TextWatcher?=null
    override fun getWatcherFromTo( listenerEditText: EditText
    ): TextWatcher {
        if (watcherDiscount==null)
            watcherDiscount=  object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                  /*  updatedEditText.removeTextChangedListener(
                        watcherDiscount
                    )*/
                    if (!viewModel.dontAllowChangesAmount) {
                        viewModel.dontAllowChangesAmount = true
                        if (listenerEditText.text.isNotEmpty())
                        viewModel.getCurrentInfoFromTo(HashMap<String, Any>().also {
                            it.put(FROM,spinnerCurrencyFrom.getSelectedItemInPosition())
                            it.put(TO,spinnerCurrencyTo.getSelectedItemInPosition())
                            it.put(AMOUNT,listenerEditText.text.toString())
                        },this@CurrencyFromImplementer)

                    }
                    else
                        viewModel.dontAllowChangesAmount = false
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    // viewModel.discountAmount.set(Utilies.formatNumbers(listenerEditText.text.toString().toDouble()))
                    //   viewModel.discountWithoutRoundAmount.set(viewModel.discountAmount.get().toString())
                }
            }
        return  watcherDiscount!!
    }

    override fun getCurrentEditText(): EditText {
        return  currentEditTextt
    }


    companion object {
        val FROM = "from"
        val TO = "to"
        val AMOUNT = "amount"
    }
}
/*class CurrencyToImplementer(val viewModel: CurrencyViewModel) :CurrencyFromToInterface{
    var watcherDiscount : TextWatcher?=null
    override fun getWatcherFromTo(updatedEditText: EditText, listenerEditText: EditText
    ): TextWatcher {
        if (watcherDiscount==null)
            watcherDiscount=  object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                   // viewModel.dontAllowChangesPercentage = true
                   if (!viewModel.dontAllowChangesAmount) // if false
                   {
                       viewModel.dontAllowChangesAmount = true

                   }
                    else viewModel.dontAllowChangesAmount = false // if true set false
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    // viewModel.discountAmount.set(Utilies.formatNumbers(listenerEditText.text.toString().toDouble()))
                    //   viewModel.discountWithoutRoundAmount.set(viewModel.discountAmount.get().toString())
                }
            }
        return  watcherDiscount!!
    }


}*/
