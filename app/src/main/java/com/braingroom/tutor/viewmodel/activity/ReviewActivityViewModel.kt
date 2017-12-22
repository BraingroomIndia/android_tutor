package com.braingroom.tutor.viewmodel.activity

import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.R
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
class ReviewActivityViewModel: ViewModel(){
    val view:ViewProvider by lazy {
        object:ViewProvider{
            override fun getView(vm: ViewModel?): Int {
                return when(vm){
                    is ReviewItemViewModel -> R.layout.item_review
                    else -> 0
                }
            }
        }
    }
    var decor: RecyclerView.ItemDecoration = EqualSpacingItemDecoration(convertDpToPixel(11).toInt(), VERTICAL)
    init{
        apiService.getReview().observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
            t.data.forEach { x ->
                item.onNext(ReviewItemViewModel(x.reviewMessage,x.rating,x.firstName))
                item.onNext(NotifyDataSetChanged())
            }
        }

        }
    }