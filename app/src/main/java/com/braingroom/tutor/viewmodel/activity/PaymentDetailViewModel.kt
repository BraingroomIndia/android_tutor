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
import io.reactivex.functions.Consumer

/*
 * Created by ashketchup on 19/12/17.
 */
class PaymentDetailViewModel(val total: String, val pending: String, val transferred: String, val tickets: String) : ViewModel() {
    /*Dummy comment */
    val isVisible: ObservableBoolean by lazy {
        ObservableBoolean(true)
    }
    val isNotVisible: ObservableBoolean by lazy {
        ObservableBoolean(false)
    }
    val nullify: Action by lazy {
        Action {
            Log.d("why", "why")
            isNotVisible.set(isVisible.get())
            isVisible.set(!isVisible.get())

        }
    }


    val startdate: DatePickerViewModel by lazy {
        if (dialogHelper != null)
            DatePickerViewModel(dialogHelper!!, "Start", "12-12-2017")
        else
            throw NullPointerException()
    }
    val enddate: DatePickerViewModel by lazy {
        if (dialogHelper != null)
            DatePickerViewModel(dialogHelper!!, "Start", "12-12-2017")
        else
            throw NullPointerException()
    }
    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return when (vm) {
                    is ItemPaymentDetailViewModel -> R.layout.item_payment_details
                    else -> 0
                }
            }
        }
    }

    init {
        apiService.getPaymentDetails(0).subscribe({ resp ->
            resp.data.forEach({ snippet -> item.onNext(ItemPaymentDetailViewModel(snippet)) })
        })
    }
}