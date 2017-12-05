package com.braingroom.tutor.viewmodel.activity

import android.util.Log
import android.view.View
import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.LoadingViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RemoveLoadingViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by ashketchup on 30/11/17.
 */
class RecyclerTester:ViewModel(){
    var views = object:ViewProvider{
        override fun getView(vm:ViewModel?):Int {
         when (vm){
             is LoadingViewModel -> return R.layout.item_loading_class_list
         }
            return 0
        }
    }
    init{
        Log.d("TAG","init")
       Observable.interval(1000,TimeUnit.MILLISECONDS).subscribe{o ->
            Log.d("Tag","next")
            item.onNext(LoadingViewModel())
           item.onNext(NotifyDataSetChanged())
        }
    }
}