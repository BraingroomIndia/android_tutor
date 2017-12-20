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
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit


class DynamicSearchSelectListViewModel(val title: String, searchHint: String, dependencyMessage: String, isMultipleSelect: Boolean, private var observableApi: DynamicSearchAPIObservable?, private val saveConsumer: Consumer<HashMap<String, Int>>, private var selectedDataMap: HashMap<String, Int>, private val fragmentHelper: FragmentHelper) : ViewModel() {

    interface DynamicSearchAPIObservable {
        fun getData(keyword: String): Observable<HashMap<String, Int>>?

    }

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
                fragmentHelper.show(title)
                start.subscribe()
            }
        }

    }
    val selectedItemsText = ObservableField("select items")

    val searchQuery = ObservableField("")
    val searchHint = ObservableField<String>(searchHint)
    val dataMap: TreeMap<String, Int> = TreeMap()
    val selectedItems: PublishSubject<SearchSelectListItemViewModel> by lazy { PublishSubject.create<SearchSelectListItemViewModel>() }

    val start = FieldUtils.toObservable(searchQuery)
            .debounce(200, TimeUnit.MILLISECONDS).map({ keyword: String ->
        observableApi?.getData(keyword)?.subscribeOn(Schedulers.io())?.observeOn(Schedulers.computation())?.map { data ->
            dataMap.clear()
            dataMap.putAll(data)
            for (name in dataMap.keys) {
                item.onNext(SearchSelectListItemViewModel(name, dataMap.get(name), false,
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
        }
    })

    init {
        this.searchHint.set(searchHint)
        selectedItemsText.set((if (TextUtils.join(" , ", selectedDataMap.keys).isNullOrBlank()) "select items" else TextUtils.join(" , ", selectedDataMap.keys)))

    }


}