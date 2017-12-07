package com.braingroom.tutor.viewmodel.activity

import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.model.req.GalleryReq
import com.braingroom.tutor.model.resp.GalleryResp
import com.braingroom.tutor.utils.CustomDrawable
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 6/12/17.
 */
class MediaViewModel:ViewModel() {
    val viewProvider: ViewProvider  by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                when (vm) {
                    is LoadingViewModel -> return R.layout.item_loading_media
                    is TextIconViewModel -> return R.layout.item_media
                    else -> {
                        Log.d("called", "wrongly called")
                        return 0
                    }
                }
            }
        }
    }
    var isVideo=false;
    val onVideo: Action by lazy{
        object:Action{
            override fun run() {
                if(!isVideo) {
                    isVideo = !isVideo
                    item.onNext(RefreshViewModel())
                    makeCall()
                }
            }
        }
    }
    val onImage: Action by lazy{
        object:Action{
            override fun run() {
                if(isVideo) {
                    isVideo = !isVideo
                    item.onNext(RefreshViewModel())
                    makeCall()
                }
            }
        }
    }
    init{
        makeCall()
    }
    fun makeCall(){
        apiService.getGallery(GalleryReq.Snippet("568",isVideo)).doOnSubscribe { disposable ->
            Log.d("called","called")
            for(i in 0..4){
                item.onNext(LoadingViewModel())
            }
            item.onNext(NotifyDataSetChanged())
            compositeDisposable.add(disposable)
        }.doOnComplete{
            item.onNext(NotifyDataSetChanged())
        }.subscribe(
                { resp ->
                    if(resp.resCode) {
                        item.onNext(RemoveLoadingViewModel())
                        for (resp: GalleryResp.Snippet in resp.data) {

                            item.onNext(TextIconViewModel(resp.mediaTitle, resp.mediaPath, object : Action {
                                override fun run() {
                                    if (isVideo) {

                                    } else {

                                    }
                                }
                            }))
                        }
                        item.onNext(NotifyDataSetChanged())
                    }
                }, { e ->
            Log.e("Error",e.message)
            e.printStackTrace()
        })
    }
}