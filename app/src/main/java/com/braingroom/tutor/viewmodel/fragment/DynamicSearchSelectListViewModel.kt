package com.braingroom.tutor.viewmodel.fragment

import android.databinding.ObservableField
import android.text.TextUtils
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.MyConsumer
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.view.fragment.DynamicSearchSelectFragment
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.SearchSelectListItemViewModel
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import com.braingroom.tutor.viewmodel.item.RefreshViewModel
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


class DynamicSearchSelectListViewModel(helperFactory: HelperFactory, val title: String, searchHint: String, dependencyMessage: String, isMultipleSelect: Boolean, private var observableApi: DynamicSearchAPIObservable?, private val saveConsumer: Consumer<HashMap<String, Int>>, private var selectedDataMap: HashMap<String, Int>) : ViewModel(helperFactory) {

    interface DynamicSearchAPIObservable {
        fun getData(keyword: String): Observable<HashMap<String, Int>>?

    }

    val onClearClicked = Action {
        selectedDataMap.clear()
        searchQuery.set("")
        selectedItemsText.set("select items")
        saveConsumer.accept(selectedDataMap)
        selectAllorClearAll.onNext(false)
    }

    val viewProvider = object : ViewProvider {
        override fun getView(vm: RecyclerViewItem?): Int {
            return R.layout.item_search_select_text;
        }
    }
    val onSaveClicked: Action by lazy {
        Action {
            saveConsumer.accept(selectedDataMap)
            navigator.removeFragment(title)

        }
    }
    val onOpenClicked: Action by lazy {
        Action {
            if (observableApi == null)
                messageHelper.showMessage(dependencyMessage)
            else {
                navigator.openFragment(title, DynamicSearchSelectFragment.newInstance(title))
                start.subscribe()
            }
        }

    }
    val selectedItemsText = ObservableField("select items")

    val searchQuery = ObservableField("")
    val searchHint = ObservableField<String>(searchHint)
    val dataMap: TreeMap<String, Int> = TreeMap()
    val selectedItems: PublishSubject<SearchSelectListItemViewModel> by lazy { PublishSubject.create<SearchSelectListItemViewModel>() }
    val selectAllorClearAll by lazy {
        PublishSubject.create<Boolean>()
    }
    val start = FieldUtils.toObservable(searchQuery)
            .debounce(200, TimeUnit.MILLISECONDS).map({ keyword: String ->
        observableApi?.getData(keyword)?.subscribeOn(Schedulers.io())?.observeOn(Schedulers.computation())?.map { data ->
            item.onNext(RefreshViewModel())
            dataMap.clear()
            dataMap.putAll(data);
            dataMap.keys
                    .filter { it.contains(keyword, true) }
                    .forEach {
                        Timber.tag(TAG).v(it)
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
                        }, selectAllorClearAll))
                    }
            item.onNext(NotifyDataSetChanged())
        }?.subscribe()
    })

    init {
        this.searchHint.set(searchHint)
        selectedItemsText.set((if (TextUtils.join(" , ", selectedDataMap.keys).isNullOrBlank()) "select items" else TextUtils.join(" , ", selectedDataMap.keys)))

    }


}