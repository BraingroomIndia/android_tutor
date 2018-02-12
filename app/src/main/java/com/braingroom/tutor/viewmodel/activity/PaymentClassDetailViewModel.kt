package com.braingroom.tutor.viewmodel.activity

import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.PaymentClassDetailResp
import com.braingroom.tutor.model.resp.PaymentClassListResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.VERTICAL
import com.braingroom.tutor.utils.convertDpToPixel
import com.braingroom.tutor.view.adapters.EqualSpacingItemDecoration
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import java.util.*

/*
 * Created by godara on 07/02/18.
 */
class PaymentClassDetailViewModel(helperFactory: HelperFactory, val classImage: String, val classTitle: String, val classId: String) : ViewModel(helperFactory) {


    val decor: RecyclerView.ItemDecoration = EqualSpacingItemDecoration(convertDpToPixel(5).toInt(), VERTICAL)


    private fun respToViewModelList(resp: PaymentClassDetailResp) = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map { ItemTransactionSummery(it) }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Booking"))
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }

    fun getView(vm: RecyclerViewItem?): Int {
        return when (vm) {
            is ItemTransactionSummery -> R.layout.item_transaction
            is LoadingViewModel -> R.layout.item_loading_media
            null -> throw NullPointerException()
            else -> throw NoSuchFieldError()
        }
    }

    init {
        FieldUtils.toObservable(callAgain).filter { pageNumber > -1 && !paginationInProgress }.subscribe({
            paginationInProgress = true
            apiService.getPaymentDetailByClass(pageNumber, classId).doOnSubscribe(this::addLoadingItems).map(this::respToViewModelList).subscribe(this::addActualItems, this::handleError)
        })
    }

    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RemoveLoadingViewModel())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }

    override fun paginate() {
        if (pageNumber > -1 && !paginationInProgress)
            callAgain.set(callAgain.get()!! + 1)
    }
}