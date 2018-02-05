package com.braingroom.tutor.viewmodel.fragment

import android.databinding.ObservableField
import android.text.TextUtils
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.MyConsumer
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.view.fragment.SearchSelectListFragment
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
import java.util.*
import java.util.concurrent.TimeUnit

/*
 * Created by ashketchup on 6/12/17.
 */
class SearchSelectListViewModel(helperFactory: HelperFactory, val title: String, val searchHint: String, val dependencyMessage: String, val isMultipleSelect: Boolean, private var observableApi: Observable<HashMap<Int, String>>?, private val saveConsumer: Consumer<HashMap<Int, String>>, private var selectedDataMap: HashMap<Int, String>) : ViewModel(helperFactory) {

    val selectAllorClearAll by lazy {
        PublishSubject.create<Boolean>()
    }

    val onClearClicked = Action {
        selectedDataMap.clear()
        searchQuery.set("")
        selectedItemsText.set("select items")
        selectAllorClearAll.onNext(false)
        saveConsumer.accept(selectedDataMap)
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
                messageHelper.showProgressDialog("Wait", "Loading")
                observableApi?.subscribeOn(Schedulers.computation())?.doFinally({ start.subscribe() })?.subscribe({ map ->
                    if (map.isEmpty()) {
                        messageHelper.showMessage("Not available")
                    } else {

                        dataMap.putAll(map)
                        searchQuery.set("")
                        navigator.openFragment(title, SearchSelectListFragment.newInstance(title))
                    }
                    messageHelper.dismissActiveProgress()
                }, { throwable ->
                    messageHelper.dismissActiveProgress()
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
                    dataMap
                            .filter { it.value.contains(keyword, true) }
                            .forEach(this::hashMapToViewModel)
                    item.onNext(NotifyDataSetChanged())
                }
    }

    val searchQuery = ObservableField("")

    val dataMap: TreeMap<Int, String> = TreeMap()
    val selectedItems: PublishSubject<SearchSelectListItemViewModel> by lazy { PublishSubject.create<SearchSelectListItemViewModel>() }


    init {
        selectedItemsText.set((if (selectedDataMap.isEmpty()) "select items" else TextUtils.join(" , ", selectedDataMap.values)))
    }


    fun refreshDataMap(dataSource: Observable<HashMap<Int, String>>?) {

        when (dataSource) {
            null -> messageHelper.showMessage(dependencyMessage)
            else -> {
                dataMap.clear()
                onClearClicked.run()
                observableApi = dataSource
            }
        }
    }

    private fun hashMapToViewModel(entry: Map.Entry<Int, String>) {
        val value = entry.value
        val key = entry.key
        item.onNext(SearchSelectListItemViewModel(value, key, selectedDataMap.containsKey(key),
                isMultipleSelect, object : MyConsumer<SearchSelectListItemViewModel> {
            override fun accept(@NonNull var1: SearchSelectListItemViewModel) {
                val isSelected = var1.isSelected.get()
                //Removing selected item
                if (isSelected) {
                    selectedDataMap.remove(var1.id)
                }
                //Adding new selected item
                else {
                    //If not multiSelect remove all selected items
                    if (!isMultipleSelect)
                        onClearClicked.run()
                    selectedDataMap.put(var1.id, var1.name)
                }
                selectedItemsText.set(TextUtils.join(" , ", selectedDataMap.values))

                //Changing selected icon
                var1.isSelected.set(!isSelected)

            }
        }, selectAllorClearAll))

    }

}
