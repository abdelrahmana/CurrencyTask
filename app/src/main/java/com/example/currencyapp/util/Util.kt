package com.example.currencyapp.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.currencyapp.R
import com.example.currencyapp.data.model.ErrorBodyResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Provides
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import javax.inject.Inject

class Util @Inject constructor(@ApplicationContext val context: Context) {
    fun getErrorBodyResponse(errorBody: ResponseBody?): String {
        val errortext = ""
        val gson = Gson()
        var errorResponse: ErrorBodyResponse? =null
        try {
            val type = object : TypeToken<ErrorBodyResponse>() {}.type

            errorResponse = gson.fromJson(errorBody?.charStream(), type) ?: (ErrorBodyResponse()
                .also {
                    it.error.info = (context.getString(R.string.default_error))


                })
        }catch (e :Exception){
            errorResponse = (ErrorBodyResponse()
                .also {
                    it.error?.info = (context.getString(R.string.default_error))


                })
        }

        var error = errorResponse?.error?.info.toString()
        return errortext?:""

    }
    fun showSnackMessages(
        activity: Activity?,
        error: String?, color : Int= android.R.color.holo_red_dark
    ) {
        if (activity != null) {
            Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.TOP)
                //.title(activity.getString(R.string.errors))
                .message(error!!)
                .backgroundColorRes(color)
                .dismissOnTapOutside()
                .duration(2500)
                .enableSwipeToDismiss()
                .enterAnimation(
                    FlashAnim.with(activity)
                        .animateBar()
                        .duration(550)
                        .alpha()
                        .overshoot()
                )
                .exitAnimation(
                    FlashAnim.with(activity)
                        .animateBar()
                        .duration(200)
                        .anticipateOvershoot()
                )
                .build().show()
        }
    }
    fun changeFragmentBack(activity: FragmentActivity, fragment: Fragment, tag: String, bundle: Bundle?, id : Int ) {

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (bundle != null) {
            fragment.arguments = bundle
        }
        transaction?.setCustomAnimations(
           R.anim.enter_from_right, R.anim.exit_to_left,
            R.anim.enter_from_left, R.anim.exit_to_right)
        //R.id.frameLayout_direction+
        transaction?.replace(id, fragment, tag)
        transaction?.addToBackStack(tag)
        //    transaction.addToBackStack(null)
        transaction?.commit()

    }

}