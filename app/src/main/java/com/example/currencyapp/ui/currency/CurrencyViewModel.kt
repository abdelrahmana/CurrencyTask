package com.example.currencyapp.ui.currency

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.model.Symbols
import com.example.currencyapp.data.repo.CurrencyRepo
import com.example.currencyapp.ui.base.BaseViewModel
import com.example.currencyapp.util.DissMissProgress
import com.example.currencyapp.util.ShowProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val homeRepo: CurrencyRepo) : BaseViewModel() {
    private val _symbolsViewModel = MutableLiveData<Symbols?>()
    val symbolsViewModel : LiveData<Symbols?> = _symbolsViewModel
    private val _getInfoFromTo = MutableLiveData<Pair<CurrencyFromToInterface,String>?>()
    val getInfoFromTo : LiveData<Pair<CurrencyFromToInterface,String>?> = _getInfoFromTo
    var dontAllowChangesAmount = false
    fun getSymbols() {
        setNetworkLoader(ShowProgress())
        viewModelScope.launch(Dispatchers.IO) {
            homeRepo.getSymbolsList { items, errors->
                items?.let {it->
                    _symbolsViewModel.postValue(it)
                }
                errors?.let {it->
                    SetError(it)
                }
                setNetworkLoader(DissMissProgress())

            }

        }
    }

    fun getCurrentInfoFromTo(hashMap: HashMap<String,Any>,currencyFromToInterface: CurrencyFromToInterface) {
        setNetworkLoader(ShowProgress())
        viewModelScope.launch(Dispatchers.IO) {
            homeRepo.getInfoFromTo(hashMap) { items, errors->
                items?.let {it->
                    _getInfoFromTo.postValue(Pair(currencyFromToInterface,items))
                }
                errors?.let {it->
                    SetError(it)
                }
                setNetworkLoader(DissMissProgress())

            }

        }
    }


    fun clearListener(fragmentActivity: FragmentActivity){
        _symbolsViewModel.removeObservers(fragmentActivity)
        _symbolsViewModel.postValue(null)
        errorViewModel.removeObservers(fragmentActivity)
        SetError(null)
        networkLoader.removeObservers(fragmentActivity)
        setNetworkLoader(null)

    }

}