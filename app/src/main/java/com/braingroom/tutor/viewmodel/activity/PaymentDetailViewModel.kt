package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableBoolean
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.DatePickerViewModel
import com.braingroom.tutor.viewmodel.item.ItemPaymentDetailViewModel
import io.reactivex.Observable
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 19/12/17.
 */
public class PaymentDetailViewModel(val total:String, val pending:String, val transferred :String, val tickets:String): ViewModel(){

    val isVisible:ObservableBoolean by lazy{
        ObservableBoolean(true)}
    val isNotVisible:ObservableBoolean by lazy{
        ObservableBoolean(false)}
    public val nullify: Action by lazy {
        Action {
            Log.d("why", "why")
            item.onNext(ItemPaymentDetailViewModel("Title", "1", "10231", "231", "100000"))
            isNotVisible.set(isVisible.get())
            isVisible.set(!isVisible.get())

        }
    }
    val startdate:DatePickerViewModel by lazy{
        if(dialogHelper!=null)
         DatePickerViewModel(dialogHelper!!,"Start","12-12-2017")
        else
            throw NullPointerException()
    }
    val enddate:DatePickerViewModel by lazy{
        if(dialogHelper!=null)
            DatePickerViewModel(dialogHelper!!,"Start","12-12-2017")
        else
            throw NullPointerException()
    }
    val view:ViewProvider by lazy {
        object :ViewProvider{
            override fun getView(vm: ViewModel?): Int {
                return when(vm){
                    is ItemPaymentDetailViewModel -> R.layout.item_payment_detail
                    else -> 0
                }
            }
        }
    }
}