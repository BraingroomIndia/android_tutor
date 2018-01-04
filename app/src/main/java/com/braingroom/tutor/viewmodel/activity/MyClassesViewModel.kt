package com.braingroom.tutor.viewmodel.activity

import android.util.Log

import com.braingroom.tutor.R
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.model.req.ClassListReq.Snippet
import com.braingroom.tutor.model.resp.ClassListResp
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel

import java.util.HashMap
import java.util.LinkedHashMap

import io.reactivex.Observable

import com.braingroom.tutor.utils.toObservable
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.functions.Consumer


class MyClassesViewModel : ViewModel() {
    val classType: ListDialogViewModel
    val classStatus: ListDialogViewModel
    val snippet = Snippet(true, true, userId)
    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return when (vm) {
                    is ClassListItemViewModel -> R.layout.item_class_list
                    is LoadingViewModel -> R.layout.item_loading_class_list
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }

    init {
        val classTypeData = LinkedHashMap<String, Int>()
        classTypeData.put("Batch", 1)
        classTypeData.put("Flexible", 0)

        val classStatusData = LinkedHashMap<String, Int>()
        classStatusData.put("Expired", 1)
        classStatusData.put("Ongoing", 0)

        classType = ListDialogViewModel("Class Type", Observable.just(ListDialogData(classTypeData)), HashMap(), false, Consumer { selectedItems ->
            if (selectedItems.values.iterator().hasNext()) {
                snippet.setBatch(selectedItems.values.iterator().next())
                reset()
                Log.d(TAG, "classType selectedItems items : " + selectedItems.values)
            }
        }, "", null)

        classStatus = ListDialogViewModel("Class Status", Observable.just(ListDialogData(classStatusData)), HashMap(), false, Consumer { selectedItems ->
            if (selectedItems.values.iterator().hasNext()) {
                snippet.setExpired(selectedItems.values.iterator().next())
                reset()
                Log.v(TAG, "classStatus selectedItems items : " + selectedItems.values)
            }
        }, "", null)
        toObservable(callAgain).filter { _ ->
            pageNumber > -1 && !paginationInProgress
        }.doOnSubscribe { disposable ->
            compositeDisposable.add(disposable)
        }.subscribe { _ ->
            apiService.getAllClasses(snippet, pageNumber).map { resp ->
                val viewModelList: ArrayList<ClassListItemViewModel> = ArrayList()
                resp.data.mapTo(viewModelList) {
                    ClassListItemViewModel(it)
                }
            }.doOnSubscribe { disposable ->
                for (i in 0..4)
                    item.onNext(LoadingViewModel())
                item.onNext(NotifyDataSetChanged())
                compositeDisposable.add(disposable)
            }.subscribe({ viewModelList ->
                item.onNext(RemoveLoadingViewModel())
                if (viewModelList.isEmpty()) {
                    if (pageNumber == 1)
                        item.onNext(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Messages"))
                    pageNumber = -1
                } else {
                    viewModelList.forEach { viewModel -> item.onNext(viewModel) }
                    pageNumber++
                }
                item.onNext(NotifyDataSetChanged())
                paginationInProgress = false
            }, { throwable -> Log.e(TAG, throwable.message, throwable) })
        }

    }

    private fun reset() {
        pageNumber = 1
        paginationInProgress = false
        item.onNext(RefreshViewModel())
        callAgain.set(callAgain.get() + 1)
    }
}
