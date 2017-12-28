package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.braingroom.tutor.R
import com.braingroom.tutor.model.resp.PaymentDetailsResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Function
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/*
 * Created by ashketchup on 19/12/17.
 */
class PaymentDetailViewModel() : ViewModel() {
    /*Dummy comment */
    val total: ObservableField<String> by lazy { ObservableField("") }
    val pending: ObservableField<String> by lazy { ObservableField("") }
    val transferred: ObservableField<String> by lazy { ObservableField("") }
    val tickets: ObservableField<String> by lazy { ObservableField("") }
    val isVisible: ObservableBoolean by lazy {
        ObservableBoolean(true)
    }
    val keyword by lazy { ObservableField("") }
    val isNotVisible: ObservableBoolean by lazy {
        ObservableBoolean(false)
    }

    val searchAction: TextView.OnEditorActionListener by lazy {
        isNotVisible.set(true)
        TextView.OnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                item.onNext(RefreshViewModel())
                callAgain.set(callAgain.get() + 1)
                return@OnEditorActionListener true;
            }
            false;
        }
    }
    val nullify: Action by lazy {
        Action {

            start.subscribe { _ ->
                apiService.getPaymentDetails(1, "", "", keyword.get()).doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                    item.onNext(RefreshViewModel())
                    for (i in 0..5) item.onNext(LoadingViewModel())
                    item.onNext(NotifyDataSetChanged())
                }.map { resp: PaymentDetailsResp ->
                    val viewModelList: ArrayList<ItemPaymentDetailViewModel> = ArrayList(8)
                    resp.data.mapTo(viewModelList) { ItemPaymentDetailViewModel(it) }
                }.subscribe({ viewModelList: List<ItemPaymentDetailViewModel> ->
                    item.onNext(RemoveLoadingViewModel())
                    viewModelList.forEach { viewModel ->
                        item.onNext(viewModel)
                    }
                }, { throwable ->
                    Log.e(TAG, throwable.message, throwable)
                }, {
                    item.onNext(NotifyDataSetChanged())
                }
                )
            }

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
            DatePickerViewModel(dialogHelper!!, "End", "12-12-2017")
        else
            throw NullPointerException()
    }
    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return when (vm) {
                    is ItemPaymentDetailViewModel -> R.layout.item_payment_details
                    is LoadingViewModel -> R.layout.item_loading_class_list
                    else -> 0
                }
            }
        }
    }

    val start = FieldUtils.toObservable(callAgain).debounce(200, TimeUnit.MILLISECONDS)

    init {

        apiService.getPaymentSummary("", "").observeOn(AndroidSchedulers.mainThread()).subscribe({ resp ->
            val data = resp.data
            total.set(data.totalAmount)
            transferred.set(data.totalAmount)
            pending.set(data.pendingAmount)
            tickets.set(data.totalTicket)
        }, { throwable ->
            Log.e(TAG, throwable.message, throwable)
        })
    }


}