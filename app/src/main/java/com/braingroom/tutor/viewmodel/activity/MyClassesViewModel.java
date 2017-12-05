package com.braingroom.tutor.viewmodel.activity;

import android.util.Log;

import com.braingroom.tutor.R;
import com.braingroom.tutor.model.data.ListDialogData;
import com.braingroom.tutor.model.req.ClassListReq.Snippet;
import com.braingroom.tutor.model.resp.ClassListResp;
import com.braingroom.tutor.view.adapters.ViewProvider;
import com.braingroom.tutor.viewmodel.ViewModel;
import com.braingroom.tutor.viewmodel.item.ClassListItemViewModel;
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel;
import com.braingroom.tutor.viewmodel.item.LoadingViewModel;
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged;
import com.braingroom.tutor.viewmodel.item.RemoveLoadingViewModel;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.Observable;

import static com.braingroom.tutor.utils.CommonUtilsKt.toObservable;


public class MyClassesViewModel extends ViewModel {
    public final ListDialogViewModel classType;
    public final ListDialogViewModel classStatus;
    public final Snippet snippet = new Snippet(true, true, getUserId());
    private int currentPageNumber = 0;
    public ViewProvider view = vm -> {
        if (vm != null) {
            if (vm instanceof ClassListItemViewModel)
                return R.layout.item_class_list;
            if (vm instanceof LoadingViewModel)
                return R.layout.item_loading_class_list;
            else
                try {
                    throw new NoSuchFieldException();
                } catch (Exception e) {
                    throw new Error();
                }
        }
        throw new NullPointerException();
    };

    public MyClassesViewModel() {


        LinkedHashMap<String, Integer> classTypeData = new LinkedHashMap<>();
        classTypeData.put("Batch", 1);
        classTypeData.put("Flexible", 0);

        LinkedHashMap<String, Integer> classStatusData = new LinkedHashMap<>();
        classStatusData.put("Expired", 1);
        classStatusData.put("Ongoing", 0);

        classType = new ListDialogViewModel("Class Type", Observable.just(new ListDialogData(classTypeData)), new HashMap<>(), false, selectedItems -> {
            if (selectedItems.values().iterator().hasNext()) {
                snippet.setBatch(selectedItems.values().iterator().next());
                reset();
                Log.d(getTAG(), "classType selectedItems items : " + selectedItems.values());
            }
        }, "", null);

        classStatus = new ListDialogViewModel("Class Status", Observable.just(new ListDialogData(classStatusData)), new HashMap<>(), false, selectedItems -> {
            if (selectedItems.values().iterator().hasNext()) {
                snippet.setExpired(selectedItems.values().iterator().next());
                reset();
                Log.d(getTAG(), "classStatus selectedItems items : " + selectedItems.values());
            }
        }, "", null);
        toObservable(getCallAgain()).subscribe(integer -> getApiService().getAllClasses(snippet, currentPageNumber).doOnSubscribe(disposable -> {
            for (int i = 0; i < 5; i++)
                getItem().onNext(new LoadingViewModel());
            getItem().onNext(new NotifyDataSetChanged());
            getCompositeDisposable().add(disposable);
            Log.d(getTAG(), "Added  Loading Items");
        }).
                doOnComplete(() -> {
                    Log.d(getTAG(), "Updating UI");
                    getItem().onNext(new NotifyDataSetChanged());
                }).
                subscribe(resp ->
                {

                    getItem().onNext(new RemoveLoadingViewModel());
                    Log.d(getTAG(), "Removed Loading Items");
                    if (resp.getResCode()) {
                        for (ClassListResp.Snippet snippet : resp.getData())
                            getItem().onNext(new ClassListItemViewModel(snippet));
                        Log.d(getTAG(), "Added Actual items");
                        currentPageNumber++;
                    }

                }, throwable -> {
                    Log.d(getTAG(), throwable.getMessage());
                    throwable.printStackTrace();
                }));
    }

    private void reset() {
        currentPageNumber = 0;
        getCallAgain().set(getCallAgain().get() + 1);
    }
}
