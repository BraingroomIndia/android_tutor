package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableBoolean
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.req.GalleryReq
import com.braingroom.tutor.model.resp.GalleryResp
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Function
import io.reactivex.subjects.ReplaySubject

/*
 * Created by ashketchup on 6/12/17.
 */
class MediaViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val viewProvider: ViewProvider  by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
                return when (vm) {
                    is LoadingViewModel -> R.layout.item_loading_media
                    is TextIconViewModel -> R.layout.item_media
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }


    var isVideo = ObservableBoolean(false)
    val onVideo: Action by lazy {
        Action {
            if (!isVideo.get()) {
                isVideo.set(!isVideo.get())
                item.onNext(RefreshViewModel())
                makeCall()
            }
        }
    }
    val onImage: Action by lazy {
        Action {
            if (isVideo.get()) {
                isVideo.set(!isVideo.get())
                item.onNext(RefreshViewModel())
                makeCall()
            }
        }
    }

    init {
        makeCall()
    }

    fun makeCall() {
        apiService.getGallery(GalleryReq(userId, isVideo.get())).doOnSubscribe(this::addLoadingItems).
                map(this::respToViewModelList).
                subscribe(this::addActualItems, this::handleError)
    }

    private fun respToViewModelList(resp: GalleryResp): List<RecyclerViewItem> = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map {
                TextIconViewModel(it.mediaTitle, it.mediaThumb, Action {
                    if (it.isVideo) {
                        navigator.openStandaloneYoutube(it.videoId, 12312)
                    } else {

                    }
                })
            }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            resp.data.map { EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Media") }
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }


    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RefreshViewModel())
        item.onNext(NotifyDataSetChanged())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }


}
