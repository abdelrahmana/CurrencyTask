package com.example.currencyapp.ui.currency

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyapp.R
import com.example.currencyapp.data.model.MigrateSymbols
import com.example.currencyapp.data.model.Symbols
import com.example.currencyapp.databinding.FragmentCurrencyFramgentBinding
import com.example.currencyapp.ui.currency.adapter.SpinnerCustomAdpter
import com.example.currencyapp.util.ImplementerFromToCurrency
import com.example.currencyapp.util.Util
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    lateinit var binding : FragmentCurrencyFramgentBinding
    @Inject lateinit var progressDialog : Dialog
    @Inject lateinit var util : Util
    var spinnerCustomAdpterFrom : SpinnerCustomAdpter?=null
    var spinnerCustomAdpterTo : SpinnerCustomAdpter?=null

    val viewModel : CurrencyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyFramgentBinding.inflate(layoutInflater,container,false)
        setObserverViewModel()
        return binding.root
    }
    private fun setObserverViewModel() {
        viewModel.getSymbols() // call to get symbols and set spinner
        viewModel.symbolsViewModel.observe(viewLifecycleOwner, Observer{
            if (it!=null){
             setSpinnerAdapter(it)

            }
        })

        viewModel.networkLoader.observe(viewLifecycleOwner,Observer{
            it?.let { progress->
                progress.setDialog(progressDialog) // open close principles

            }
        })

        viewModel.errorViewModel.observe(viewLifecycleOwner, Observer{
            it?.let { errorMessage->
                // progress.setDialog(progressDialog) // open close principles
                util.showSnackMessages(requireActivity(),errorMessage)

            }
        })
        viewModel.getInfoFromTo.observe(viewLifecycleOwner, Observer{
            it?.let { pair->
                // progress.setDialog(progressDialog) // open close principles
                pair.first.getCurrentEditText().setText(pair.second)

            }
        })
    }

    val migrationFrom : (MigrateSymbols)->Unit = {currentSelection->
        binding.valueSelectedFrom.text = currentSelection.value
    }
    val migrationTo : (MigrateSymbols)->Unit = {currentSelection->
        binding.valueSelectedCity.text = currentSelection.value

    }
    private fun setSpinnerAdapter(it: Symbols) {
        spinnerCustomAdpterFrom = SpinnerCustomAdpter(requireContext(), R.layout.spinner_item, getArrayListMigration(it))
        spinnerCustomAdpterTo = SpinnerCustomAdpter(requireContext(), R.layout.spinner_item, getArrayListMigration(it))

       val implementationFrom =  ImplementerFromToCurrency(spinnerCustomAdpterFrom!!,binding.spinnerFrom)
        implementationFrom.setTypeSelected(
       binding.containerClickFrom,migrationFrom)
       val implementationTo =  ImplementerFromToCurrency(spinnerCustomAdpterTo!!,binding.spinnerTo)
           implementationTo.setTypeSelected(
            binding.containerClickTo,migrationTo)

        binding.fromEditText.addTextChangedListener(CurrencyFromImplementer(viewModel,implementationFrom
            ,implementationTo,binding.toEditText).getWatcherFromTo(binding.fromEditText))
        // to change reflect
        binding.toEditText.addTextChangedListener(CurrencyFromImplementer(viewModel,implementationFrom
            ,implementationTo,binding.fromEditText).getWatcherFromTo(binding.toEditText))

    }

    private fun getArrayListMigration(symbols: Symbols) : ArrayList<MigrateSymbols> {
        val oMapper = ObjectMapper()

       val map =  oMapper.convertValue(symbols,Map::class.java)
        return  ArrayList<MigrateSymbols>().also {
            map.forEach{currentItem->
                it.add(MigrateSymbols(currentItem.key.toString(),currentItem.value.toString()))

            }

        }
    }

}