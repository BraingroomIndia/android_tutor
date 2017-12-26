package com.braingroom.tutor.viewmodel.activity

import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.VERTICAL
import com.braingroom.tutor.utils.convertDpToPixel
import com.braingroom.tutor.view.adapters.EqualSpacingItemDecoration
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.ReviewItemViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by ashketchup on 22/12/17.
 */
class ReviewActivityViewModel : ViewModel() {
    var paginationInProgress = false
    var pageNumber = 1;
    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return when (vm) {
                    is ReviewItemViewModel -> R.layout.item_review
                    else -> 0
                }
            }
        }
    }
    val decor: RecyclerView.ItemDecoration = EqualSpacingItemDecoration(convertDpToPixel(11).toInt(), VERTICAL)

    init {
        FieldUtils.toObservable(callAgain).filter({ _ -> pageNumber > -1 && !paginationInProgress }).subscribe({
            paginationInProgress = true
            apiService.getReview(pageNumber).observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
                t.data.forEach { x ->
                    item.onNext(ReviewItemViewModel(x.reviewMessage, x.rating, x.firstName))
                    item.onNext(NotifyDataSetChanged())
                }
                paginationInProgress = false
                if (t == null || t.data == null || t.data.isEmpty())
                    pageNumber = -1
                else pageNumber++
            }

        })
    }

    override fun paginate() {
        super.paginate()
        callAgain.set(callAgain.get()!! + 1)
    }
}