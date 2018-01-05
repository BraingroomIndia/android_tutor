package com.braingroom.tutor.viewmodel.activity

import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.R
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

/*
 * Created by ashketchup on 22/12/17.
 */
class ReviewActivityViewModel : ViewModel() {

    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
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
        FieldUtils.toObservable(callAgain).filter({ _ -> pageNumber > -1 && !paginationInProgress }).doOnSubscribe { disposable ->
            compositeDisposable.add(disposable)
            (0..5).forEach { _ -> item.onNext(LoadingViewModel()) }
            item.onNext(NotifyDataSetChanged())

        }.subscribe({
            paginationInProgress = true
            apiService.getReview(pageNumber).map { resp ->
                if (resp.resCode) {
                    resp.data.map { snippet ->
                        if (isEmpty(snippet.classId))
                            ReviewItemViewModel(snippet.reviewMessage, snippet.rating, snippet.firstName, "Vendor Review")
                        else
                            ReviewItemViewModel(snippet.reviewMessage, snippet.rating, snippet.firstName, snippet.classTopic)
                    }
                } else (pageNumber == 1)
                Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Review Found"))
            }.subscribe { viewModelList ->
                item.onNext(RemoveLoadingViewModel())
                viewModelList.forEach { viewModel ->
                    item.onNext(viewModel)
                }
                item.onNext(NotifyDataSetChanged())
                paginationInProgress = false
                if (viewModelList[0] is EmptyItemViewModel)
                    pageNumber = -1
                else pageNumber++
            }

        })
    }

    override fun paginate() {
        super.paginate()
        if (pageNumber > -1 && !paginationInProgress)
            callAgain.set(callAgain.get()!! + 1)
    }
}