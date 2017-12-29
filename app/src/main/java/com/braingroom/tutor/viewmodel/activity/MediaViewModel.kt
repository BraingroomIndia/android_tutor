package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableBoolean
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.model.req.GalleryReq
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 6/12/17.
 */
class MediaViewModel : ViewModel() {
    val viewProvider: ViewProvider  by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
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
        apiService.getGallery(GalleryReq.Snippet("568", isVideo.get())).doOnSubscribe { disposable ->
            Log.d("called", "called")
            for (i in 0..4) {
                item.onNext(LoadingViewModel())
            }
            item.onNext(NotifyDataSetChanged())
            compositeDisposable.add(disposable)
        }.doOnComplete {
            item.onNext(NotifyDataSetChanged())
        }.subscribe(
                { resp ->
                    if (resp.resCode) {
                        item.onNext(RemoveLoadingViewModel())
                        for (snippet in resp.data) {

                            item.onNext(TextIconViewModel(snippet.mediaTitle, snippet.mediaPath, object : Action {
                                override fun run() {
                                    if (isVideo.get()) {

                                    } else {

                                    }
                                }
                            }))
                        }
                        item.onNext(NotifyDataSetChanged())
                    }
                }, { e ->
            Log.e("Error", e.message)
            e.printStackTrace()
        })
    }
}
