package com.braingroom.tutor.viewmodel.fragment

import android.databinding.ObservableField
import android.text.TextUtils
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.MyConsumer
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.SearchSelectListItemViewModel
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RefreshViewModel
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

/*
 * Created by ashketchup on 6/12/17.
 */
class SearchSelectListViewModel(val title: String, val searchHint: String, val dependencyMessage: String, isMultipleSelect: Boolean, private var observableApi: Observable<HashMap<String, Int>>?, private val saveConsumer: Consumer<HashMap<String, Int>>, private var selectedDataMap: HashMap<String, Int>, private val fragmentHelper: FragmentHelper) : ViewModel() {


    val onClearClicked = Action {
        selectedDataMap.clear()
        searchQuery.set("")
        selectedItemsText.set("select items")
        saveConsumer.accept(selectedDataMap)
    }

    val viewProvider = object : ViewProvider {
        override fun getView(vm: ViewModel?): Int {
            return R.layout.item_search_select_text;
        }
    }
    val onSaveClicked: Action by lazy {
        Action {
            saveConsumer.accept(selectedDataMap)
            fragmentHelper.remove(title)
        }
    }
    val onOpenClicked: Action by lazy {
        Action {
            if (observableApi == null)
                messageHelper?.showMessage(dependencyMessage)
            else {
                messageHelper?.showProgressDialog("Wait", "Loading")
                observableApi?.subscribeOn(Schedulers.computation())?.doFinally({ start.subscribe() })?.subscribe({ map ->
                    if (map.isEmpty()) {
                        messageHelper?.showMessage("Not available")
                    } else {
                        fragmentHelper.show(title)
                        dataMap.putAll(map)
                        searchQuery.set("")
                    }
                    messageHelper?.dismissActiveProgress()
                }, { throwable ->
                    messageHelper?.dismissActiveProgress()
                    Log.e("Search select List VM", "accept: " + throwable.message)
                })

            }

        }
    }
    val selectedItemsText = ObservableField("select items")


    val start by lazy {
        FieldUtils.toObservable(searchQuery).doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.
                subscribeOn(Schedulers.computation()).
                map { keyword: String ->
                    item.onNext(RefreshViewModel())
                    dataMap.keys
                            .filter { it.contains(keyword, true) }
                            .forEach {
                                Log.v(TAG, it)
                                item.onNext(SearchSelectListItemViewModel(it, dataMap[it], selectedDataMap.containsKey(it),
                                        isMultipleSelect, object : MyConsumer<SearchSelectListItemViewModel> {
                                    override fun accept(@NonNull var1: SearchSelectListItemViewModel) {
                                        if (var1.isSelected.get())
                                            selectedDataMap.remove(var1.name)
                                        else {
                                            if (!isMultipleSelect)
                                                selectedDataMap.clear()
                                            selectedDataMap.put(var1.name, var1.id)
                                        }
                                        selectedItemsText.set(TextUtils.join(" , ", selectedDataMap.keys))
                                        selectedItems.onNext(var1)

                                    }
                                }, selectedItems))
                            }
                    item.onNext(NotifyDataSetChanged())
                }
    }

    val searchQuery = ObservableField("")

    val dataMap: TreeMap<String, Int> = TreeMap()
    val selectedItems: PublishSubject<SearchSelectListItemViewModel> by lazy { PublishSubject.create<SearchSelectListItemViewModel>() }


    init {
        selectedItemsText.set((if (TextUtils.join(" , ", selectedDataMap.keys).isNullOrBlank()) "select items" else TextUtils.join(" , ", selectedDataMap.keys)))
    }


    fun refreshDataMap(dataSource: Observable<HashMap<String, Int>>?) {

        when (dataSource) {
            null -> messageHelper?.showMessage(dependencyMessage)
            else -> {
                dataMap.clear()
                selectedDataMap.clear()
                observableApi = dataSource
            }
        }
    }

}
