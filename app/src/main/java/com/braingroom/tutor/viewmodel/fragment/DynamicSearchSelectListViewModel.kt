package com.braingroom.tutor.viewmodel.fragment

import android.databinding.ObservableField
import android.text.TextUtils
import com.braingroom.tutor.model.resp.CommonIdResp
import com.braingroom.tutor.utils.FieldUtils
import com.braingroom.tutor.utils.MessageHelper
import com.braingroom.tutor.utils.MyConsumer
import com.braingroom.tutor.utils.Navigator
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.SearchSelectListItemViewModel
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class DynamicSearchSelectListViewModel constructor() : ViewModel() {

    lateinit var onSaveClicked: Action
    val onClearClicked: Action by lazy{
        Action {  }
    }
    val onOpenClicked: Action by lazy {
        Action {  }
    }
    lateinit var searchResults: Observable<List<ViewModel>>
    val selectedItemsText = ObservableField("select items")
    val searchQuery = ObservableField("")
    val searchHint = ObservableField<String>()
    val title = ObservableField<String>()
    val dataMap: MutableMap<String, Int> = java.util.HashMap()
    val selectedDataMap = java.util.HashMap<String, Int>()
    val FRAGMENT_TITLE_COLLEGE = "College"
    val FRAGMENT_TITLE_SCHOOL = "Schools"
    val FRAGMENT_TITLE_LEARNER = "Post by"
    val FRAGMENT_TITLE_Vendor = "Posted by"

    internal var singleSelect: PublishSubject<SearchSelectListItemViewModel> = PublishSubject.create()
    internal var multipleSelect: PublishSubject<SearchSelectListItemViewModel> = PublishSubject.create()
    internal var selectClear = PublishSubject.create<String>()

    //    Observable<HashMap<String, Int>> apiObservable;
    init{

    }
    constructor(title:String,messageHelper: MessageHelper,navigator: Navigator,searchHint:String,isMultipleSelect:Boolean,dependencySelectMessage:String,saveConsumer: Consumer<HashMap<String , Int>>,fragmentHelper: FragmentHelper) : this(){
        this.searchHint.set(searchHint)
        this.title.set(title)
//      this.apiObservable = apiObservableArg;
//      refreshDataMap(this.apiObservable);
        onSaveClicked=Action {
            searchQuery.set("")
            saveConsumer.accept(selectedDataMap)
            fragmentHelper.remove(title) }
        searchResults = FieldUtils.toObservable(searchQuery)
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter(Predicate<String> { true }).flatMap(Function<String, Observable<List<ViewModel>>> { keyword ->
                requestData(keyword)!!.map(Function<Any, List<ViewModel>> { resp ->
                    val resMap = java.util.HashMap<String, Int>()
                    if(resp is CommonIdResp)
                    for (snippet in resp.getData()) {
                        snippet as CommonIdResp.Snippet;
                        resMap.put(snippet.getTextValue(),snippet.getId())
                    }
                    dataMap.clear()
                    dataMap.putAll(resMap)
                    val results = mutableListOf<ViewModel>()
                    val nameList = ArrayList(dataMap.keys)
                    for (name in nameList) {
                        results.add(SearchSelectListItemViewModel(name, dataMap[name]!!, selectedDataMap.containsKey(name), object : MyConsumer<SearchSelectListItemViewModel> {
                            override fun accept(@NonNull viewModel: SearchSelectListItemViewModel) {
                            if (viewModel.isSelected.get())
                                selectedDataMap.remove(viewModel.name)
                            else {
                                if (!isMultipleSelect) {
                                    selectedDataMap.clear()
                                    selectedDataMap.put(viewModel.name, viewModel.id)
                                }
                            }

                            selectedItemsText.set(TextUtils.join(" , ", selectedDataMap.keys))
                            if (isMultipleSelect)
                                multipleSelect.onNext(viewModel)
                            else
                                singleSelect.onNext(viewModel)
                        }
                    }, singleSelect, multipleSelect, selectClear))

                }
                    results
            })
        })
    }

    private fun requestData(keyword: String): Observable<CommonIdResp>? {
        if (FRAGMENT_TITLE_COLLEGE == title.get())
            return apiService.getInstitute(keyword)
        if (FRAGMENT_TITLE_SCHOOL.equals(title.get(), ignoreCase = true))
            return apiService.getSchools(keyword)
        if (FRAGMENT_TITLE_LEARNER == title.get())
            return apiService.getLearner(keyword)
        return if (FRAGMENT_TITLE_Vendor == title.get()) apiService.geTutor(keyword).mergeWith(apiService.getLearner(keyword)) else null
    }
}