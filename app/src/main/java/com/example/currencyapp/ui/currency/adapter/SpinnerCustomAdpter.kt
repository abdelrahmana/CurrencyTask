package com.example.currencyapp.ui.currency.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.currencyapp.data.model.MigrateSymbols
import com.example.currencyapp.databinding.SpinnerItemBinding

class SpinnerCustomAdpter(context: Context?, resource: Int, val list: ArrayList<MigrateSymbols>)
    : ArrayAdapter<MigrateSymbols>(context!!, resource, list) {
   override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       var view :View?=convertView
       var binding : SpinnerItemBinding?=null
       var viewHolder : ViewHolder?=null
       try {
     if (convertView==null) {
               val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
             // view = inflater.inflate(R.layout.spinner_item, parent, false)
           binding = SpinnerItemBinding.inflate(inflater,parent,false)

         viewHolder = ViewHolder(binding)
         viewHolder.binding.root.tag = viewHolder
       } else  {
         viewHolder = convertView.tag as ViewHolder
     }
         //  view?.findViewById<TextView>(R.id.st_tv)?.text = list[position].getTitle() // for each time get the title and save it in text
           viewHolder.binding.stTv.text = list[position].value?:""

           // list[position].name?:list[position].name_ar?:""
       } catch (e: Exception) {

       }
        return viewHolder?.binding?.root!! //view!!
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
      /*  val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.spinner_item, parent, false)
        view.findViewById<TextView>(R.id.st_tv).text = list[position].getTitle()
        return view*/
        var binding : SpinnerItemBinding?=null
        var viewHolder : ViewHolder?=null
        try {
            if (convertView==null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                //   view = inflater.inflate(R.layout.spinner_item, parent, false)
                binding = SpinnerItemBinding.inflate(inflater,parent,false)

                viewHolder = ViewHolder(binding)
                viewHolder.binding.root.tag = viewHolder
            }else  {
                viewHolder = convertView.tag as ViewHolder

            }
            //  view?.findViewById<TextView>(R.id.st_tv)?.text = list[position].getTitle() // for each time get the title and save it in text

            viewHolder.binding.stTv.text = list[position].value?:""
        } catch (e: Exception) {
        }
        return viewHolder?.binding?.root!! //view!!
    }


     class ViewHolder (val binding: SpinnerItemBinding) {
    }

}
