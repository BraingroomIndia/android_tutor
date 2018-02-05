package com.braingroom.tutor.viewmodel.activity

import android.os.Bundle
import android.util.Log

import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.model.req.ClassListReq.Snippet
import com.braingroom.tutor.model.resp.BaseResp
import com.braingroom.tutor.model.resp.ClassListResp
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel

import io.reactivex.Observable

import com.braingroom.tutor.utils.toObservable
import com.braingroom.tutor.view.activity.ClassDetailActivity
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import java.util.*


class MyClassesViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val classType: ListDialogViewModel
    val classStatus: ListDialogViewModel
    val snippet = Snippet(userId)
    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
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
        object : Consumer<MyProfileItem> {
            override fun accept(t: MyProfileItem?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        val classTypeData = LinkedHashMap<Int, String>()
        classTypeData.put(1, "Batch")
        classTypeData.put(2, "Flexible")


        val classStatusData = LinkedHashMap<Int, String>()
        classStatusData.put(1, "Expired")
        classStatusData.put(2, "Ongoing")

        classType = ListDialogViewModel(helperFactory, "Class Type", Observable.just(ListDialogData(classTypeData)), HashMap(), false, Consumer {
            it.keys.forEach { snippet.setBatch(it) }
            reset()
        }, "", null)

        classStatus = ListDialogViewModel(helperFactory, "Class Status", Observable.just(ListDialogData(classStatusData)), HashMap(), false, Consumer {
            it.keys.forEach { snippet.setExpired(it) }
            reset()
        }, "", null)
        toObservable(callAgain).filter { pageNumber > -1 && !paginationInProgress }.doOnSubscribe { compositeDisposable.add(it) }.
                subscribe {
                    apiService.getAllClasses(snippet, pageNumber).
                            map(this::respToViewModelList).
                            doOnSubscribe(this::addLoadingItems).
                            subscribe(this::addActualItems, this::handleError)
                }

    }

    private fun reset() {
        pageNumber = 1
        paginationInProgress = false
        item.onNext(RefreshViewModel())
        callAgain.set(callAgain.get() + 1)
    }

    private fun respToViewModelList(resp: ClassListResp): List<RecyclerViewItem> = when {
        resp.resCode -> {
            pageNumber++
            resp.data.map {
                ClassListItemViewModel(it, Action {
                    apiService.getClassDetail(it.classId.toString()).doOnSubscribe {
                        compositeDisposable.add(it)
                        messageHelper.showProgressDialog("Wait", "Loading")
                    }.doOnError(this::handleError).doOnError { messageHelper.dismissActiveProgress() }.observeOn(AndroidSchedulers.mainThread()).
                            subscribe {
                                if (it.resCode) {
                                    messageHelper.dismissActiveProgress()
                                    val data = Bundle()
                                    data.putSerializable("classData", it.data)
                                    navigator.navigateActivity(ClassDetailActivity::class.java, data)
                                } else messageHelper.showMessage(it.resMsg)
                            }

                })
            }
        }
        pageNumber == 1 -> {
            pageNumber = -1
            Collections.singletonList(EmptyItemViewModel("", R.drawable.ic_no_post_64dp, "No Classes"))
        }
        else -> {
            pageNumber = -1
            ArrayList()
        }
    }


    private fun addActualItems(viewModelList: List<RecyclerViewItem>) {
        item.onNext(RemoveLoadingViewModel())
        item.onNext(NotifyDataSetChanged())
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
        paginationInProgress = false
    }

    private fun navigateToClassDetail() {
        apiService.getClassDetail("743").doOnSubscribe {
            compositeDisposable.add(it)
            messageHelper.showProgressDialog("Wait", "Loading")
        }.doOnError(this::handleError).doOnError { messageHelper.dismissActiveProgress() }.observeOn(AndroidSchedulers.mainThread()).
                subscribe {
                    if (it.resCode) {
                        messageHelper.dismissActiveProgress()
                        val data = Bundle()
                        data.putSerializable("classData", it.data)
                        navigator.navigateActivity(ClassDetailActivity::class.java, data)
                    } else messageHelper.showMessage(it.resMsg)
                }
    }

}

