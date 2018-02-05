package com.braingroom.tutor.viewmodel.activity

import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.NotificationListResp
import com.braingroom.tutor.model.resp.ReviewGetResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.VERTICAL
import com.braingroom.tutor.utils.convertDpToPixel
import com.braingroom.tutor.utils.isEmpty
import com.braingroom.tutor.view.adapters.EqualSpacingItemDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

/*
 * Created by ashketchup on 22/12/17.
 */
class ReviewActivityViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {

    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
                return when (vm) {
                    is ReviewItemViewModel -> R.layout.item_review
                    is LoadingViewModel -> R.layout.item_loading_media
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }
    val decor: RecyclerView.ItemDecoration = EqualSpacingItemDecoration(convertDpToPixel(11).toInt(), VERTICAL)

    init {
        FieldUtils.toObservable(callAgain).filter { pageNumber > -1 && !paginationInProgress }.subscribe({
            paginationInProgress = true
            apiService.getReview(pageNumber).doOnSubscribe(this::addLoadingItems).map(this::respToViewModelList).subscribe(this::addActualItems, this::handleError)
        })
    }

    override fun paginate() {
        super.paginate()
        if (pageNumber > -1 && !paginationInProgress)
            callAgain.set(callAgain.get()!! + 1)
    }

    private fun respToViewModelList(resp: ReviewGetResp): List<RecyclerViewItem> = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map { ReviewItemViewModel(it.reviewMessage, it.rating, it.firstName, it.classTopic) }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Review Found"))
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
}