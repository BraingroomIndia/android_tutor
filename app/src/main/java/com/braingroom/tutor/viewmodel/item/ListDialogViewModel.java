package com.braingroom.tutor.viewmodel.item;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.braingroom.tutor.model.data.ListDialogData;
import com.braingroom.tutor.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/*
 * Created by godara on 13/10/17.
 */

public class ListDialogViewModel extends ViewModel {

    @Nullable
    private ListDialogData dialogData;
    @NonNull
    private HashMap<String, Integer> selectedItemsMap;

    public ObservableBoolean editable = new ObservableBoolean(true);
    public ObservableBoolean visible = new ObservableBoolean(true);

    @Nullable
    private Observable<ListDialogData> source;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Disposable disposable;
    @Nullable
    private Consumer<HashMap<String, Integer>> resultConsumer;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final boolean isMultiSelect;

    @NonNull
    public final String title;
    public final ObservableField<String> selectedItemsText = new ObservableField<>("");
    public final Action onOpenClick;
    private final String dependencyMessage;

    public ListDialogViewModel(@Nullable final String title, @Nullable Observable<ListDialogData> sourceObservable, @Nullable HashMap<String, Integer> selectedItemsMap, final boolean isMultiSelect
            , @Nullable Consumer<HashMap<String, Integer>> resultConsumer, @Nullable String dependencyMessage) {
        this.title = title != null && !title.trim().isEmpty() ? title : "";
        this.selectedItemsMap = selectedItemsMap != null && !selectedItemsMap.isEmpty() ? selectedItemsMap : new HashMap<>();
        this.resultConsumer = resultConsumer;
        this.isMultiSelect = isMultiSelect;
        this.dependencyMessage = dependencyMessage;
        setSourceObservable(sourceObservable);
        onOpenClick = this::show;
        setSelectedItemsText();

    }


    @SuppressWarnings("WeakerAccess")
    public void setSourceObservable(Observable<ListDialogData> sourceObservable) {
        if (sourceObservable == null) {
            return;
        }
        source = sourceObservable.doOnNext(items -> {
            getMessageHelper().dismissActiveProgress();
            dialogData = items;
            if (!dialogData.getItems().isEmpty()) {
                getDialogHelper().showSingleSelectList(title, new ArrayList<>(dialogData.getItems().keySet())
                        , getSelectedIndex(), "Done");

            }

        }).doOnError(throwable -> Log.e("ListDialogViewmodel", "onError in source observable", throwable)).onErrorResumeNext(Observable.empty())
                .share();


    }

    @SuppressWarnings("WeakerAccess")
    public void show() {
        if (!editable.get())
            return;
        if (source == null) {
            getMessageHelper().showDismissInfo(dependencyMessage);
            return;
        }
        getDialogHelper().setViewModel(this);


        disposable = source.subscribe();
    }

    @SuppressWarnings("WeakerAccess")
    public Integer[] getSelectedIndex() {
        if (dialogData == null) return new Integer[]{-1};
        List<String> itemList = new ArrayList<>(dialogData.getItems().keySet());
        List<Integer> selectedIdx = new ArrayList<>();
        for (String name : selectedItemsMap.keySet()) {
            selectedIdx.add(itemList.indexOf(name));
        }
        if (selectedIdx.size() == 0)
            return new Integer[]{};
        else
            return selectedIdx.toArray(new Integer[selectedIdx.size()]);
    }

    @SuppressWarnings("unused")
    public void setSelectedItemsMap(@Nullable HashMap<String, Integer> selectedItemsMap) {
        if (selectedItemsMap != null)
            this.selectedItemsMap = selectedItemsMap;
        setSelectedItemsText();
        try {
            if (resultConsumer != null)
                resultConsumer.accept(selectedItemsMap);
        } catch (Exception e) {
            Log.e(getTAG(), e.getLocalizedMessage());
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void setSelectedItemsText() {

        List<String> itemList = new ArrayList<>(selectedItemsMap.keySet());
        if (itemList.size() == 0) selectedItemsText.set("select item/items");
        else if (itemList.size() == 1) selectedItemsText.set(itemList.get(0));
        else selectedItemsText.set(itemList.get(0) + " & " + (itemList.size() - 1) + " others");


    }

    public void setSelectedItems(Integer[] idxs) {

        getMessageHelper().dismissActiveProgress();
        if (dialogData == null)
            return;
        List<String> itemList;
        itemList = new ArrayList<>(dialogData.getItems().keySet());
        if (idxs[0] == -1) return;
        selectedItemsMap.clear();
        for (int idx : idxs) {
            selectedItemsMap.put(itemList.get(idx), dialogData.getItems().get(itemList.get(idx)));
        }
        setSelectedItemsText();
        // this will be called when atleast one item is selected
        try {
            if (resultConsumer != null)
                resultConsumer.accept(selectedItemsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void handleOkClick() {
        dismiss();
    }

    public void dismiss() {
        if (!disposable.isDisposed())
            disposable.dispose();
    }
}
