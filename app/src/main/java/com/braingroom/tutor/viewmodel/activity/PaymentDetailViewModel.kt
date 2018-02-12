package com.braingroom.tutor.viewmodel.activity

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.PaymentClassListResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.classIdBundleData
import com.braingroom.tutor.utils.classImage
import com.braingroom.tutor.utils.className
import com.braingroom.tutor.view.activity.PaymentClassDetailActivity
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import timber.log.Timber
import java.util.ArrayList

/*
 * Created by ashketchup on 19/12/17.
 */
class PaymentDetailViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
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

    /*   val searchAction by lazy {
           isNotVisible.set(true)
           TextView.OnEditorActionListener { p0, p1, p2 ->
               if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                   isNotVisible.set(true)
                   isVisible.set(false)
                   reset()
                   (p0.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(p0.windowToken, 0) // hide keyborad
                   return@OnEditorActionListener true;
               }
               false;
           }
       }*/

    fun searchAction(textView: TextView, actionId: Int, keyEvent: KeyEvent): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            isNotVisible.set(true)
            isVisible.set(false)
            reset()
            (textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(textView.windowToken, 0) // hide keyborad
            true;
        } else false
    }

    val nullify: Action by lazy {
        Action {
            isNotVisible.set(isVisible.get())
            isVisible.set(!isVisible.get())
        }
    }


    val startdate: DatePickerViewModel by lazy {

        DatePickerViewModel(helperFactory, "Start", "12-12-2017", Runnable { })

    }
    val enddate: DatePickerViewModel by lazy {

        DatePickerViewModel(helperFactory, "End", "12-12-2017", Runnable { })

    }
    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
                return when (vm) {
                    is ItemPaymentDetailViewModel -> R.layout.item_payment_details
                    is LoadingViewModel -> R.layout.item_loading_class_list
                    else -> 0
                }
            }
        }
    }


    init {

        apiService.getPaymentSummary("", "").observeOn(AndroidSchedulers.mainThread()).subscribe({ resp ->
            val data = resp.data
            total.set(data.totalAmount)
            transferred.set(data.totalAmount)
            pending.set(data.pendingAmount)
            tickets.set(data.totalTicket)
        }, { throwable ->
            Timber.tag(TAG).e(throwable, throwable.message)
        })

        FieldUtils.toObservable(callAgain).filter({ _ -> pageNumber > -1 && !paginationInProgress }).subscribe { _ ->
            apiService.getPaymentDetails(pageNumber, startdate.title.get(), enddate.title.get(), keyword.get()).
                    doOnSubscribe { disposable ->
                        compositeDisposable.add(disposable)
                        for (i in 0..5) item.onNext(LoadingViewModel())
                        item.onNext(NotifyDataSetChanged())
                    }.map { resp: PaymentClassListResp ->
                val viewModelList: ArrayList<ItemPaymentDetailViewModel> = ArrayList(8)
                resp.data.mapTo(viewModelList) {
                    ItemPaymentDetailViewModel(it, Action {
                        val data = Bundle();
                        data.putString(classIdBundleData, it.classId)
                        data.putString(className, it.classTopic)
                        data.putString(classImage, it.classImage)
                        navigator.navigateActivity(PaymentClassDetailActivity::class.java, data)
                    })
                }
            }.subscribe(
                    { viewModelList ->
                        item.onNext(RemoveLoadingViewModel())
                        if (viewModelList.isEmpty()) {
                            if (pageNumber == 1)
                                item.onNext(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Messages"))
                            pageNumber = -1
                        } else {
                            viewModelList.forEach { viewModel -> item.onNext(viewModel) }
                            pageNumber++
                        }
                        item.onNext(NotifyDataSetChanged())
                        paginationInProgress = false
                    }, { throwable -> Timber.tag(TAG).e(throwable, throwable.message) }
            )
        }
    }

    private fun reset() {
        pageNumber = 1
        paginationInProgress = false
        item.onNext(RefreshViewModel())
        callAgain.set(callAgain.get() + 1)
    }

}
