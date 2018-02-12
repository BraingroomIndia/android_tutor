package com.braingroom.tutor.viewmodel.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.PaymentClassListResp
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.PaymentClassDetailActivity
import com.braingroom.tutor.view.adapters.EqualSpacingItemDecoration
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.functions.Action
import java.util.*

/*
 * Created by godara on 06/02/18.
 */
class PaymentClassListViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    fun getView(vm: RecyclerViewItem?): Int {
        return when (vm) {
            is ItemPaymentDetailViewModel -> R.layout.item_payment_details
            is LoadingViewModel -> R.layout.item_loading_media
            null -> throw NullPointerException()
            else -> throw NoSuchFieldError()
        }
    }

    var keyWord: String = ""

    val firstDate by lazy {
        DatePickerViewModel(helperFactory, "Start Date", "", this::reset);
    }
    val secondDate by lazy {
        DatePickerViewModel(helperFactory, "End date", "", this::reset)
    }

    init {
        FieldUtils.toObservable(callAgain).filter { pageNumber > -1 && !paginationInProgress }.subscribe({
            paginationInProgress = true
            apiService.getPaymentDetails(pageNumber, firstDate.title.get(), secondDate.title.get(), keyWord).doOnSubscribe(this::addLoadingItems).map(this::respToViewModelList).subscribe(this::addActualItems, this::handleError)
        })
    }

    val decor: RecyclerView.ItemDecoration = EqualSpacingItemDecoration(convertDpToPixel(2).toInt(), VERTICAL)

    private fun respToViewModelList(resp: PaymentClassListResp) = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map {
                ItemPaymentDetailViewModel(it, Action {
                    val data = Bundle();
                    data.putString(classIdBundleData, it.classId)
                    data.putString(className, it.classTopic)
                    data.putString(classImage, it.classImage)
                    navigator.navigateActivity(PaymentClassDetailActivity::class.java, data)
                })
            }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Booking "))
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }

    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RemoveLoadingViewModel())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }

    fun reset() {
        if (paginationInProgress)
            return
        item.onNext(RefreshViewModel())
        pageNumber = 0
        paginate()
    }

    public override fun paginate() {
        if (pageNumber > -1 && !paginationInProgress)
            callAgain.set(callAgain.get()!! + 1)
    }

}