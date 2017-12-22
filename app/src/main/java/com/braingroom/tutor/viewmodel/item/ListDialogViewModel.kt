package com.braingroom.tutor.viewmodel.item

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log

import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.viewmodel.ViewModel

import java.util.ArrayList
import java.util.HashMap

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/*
 * Created by godara on 13/10/17.
 */

class ListDialogViewModel(val title: String?, sourceObservable: Observable<ListDialogData>?,
                          private var selectedItemsMap: HashMap<String, Int>, private val isMultiSelect: Boolean,
                          private val resultConsumer: Consumer<HashMap<String, Int>>?, private val dependencyMessage: String?,
                          private val positiveText: String?) : ViewModel() {

    private var dialogData: ListDialogData? = null
    val editable = ObservableBoolean(true)
    @Suppress("unused")
    val visible = ObservableBoolean(true)

    private var source: Observable<ListDialogData>? = null


    val selectedItemsText = ObservableField("")
    val onOpenClick: Action

    private val selectedIndex: Array<Int>
        get() {
            if (dialogData == null) return arrayOf(-1)
            val selectedIdx = selectedItemsMap.values.toTypedArray()
            return if (selectedIdx.isEmpty())
                arrayOf(-1)
            else
                selectedIdx
        }

    init {
        setSourceObservable(sourceObservable)
        onOpenClick = Action { this.show() }
        setSelectedItemsText()
    }


    @Suppress("MemberVisibilityCanPrivate")
    fun setSourceObservable(sourceObservable: Observable<ListDialogData>?) {
        if (sourceObservable == null) {
            return
        }
        source = sourceObservable.observeOn(AndroidSchedulers.mainThread()).doOnNext { items ->
            messageHelper?.dismissActiveProgress()
            dialogData = items
            if (dialogData?.getItems()?.isEmpty() == false) {
                if (isMultiSelect)
                    dialogHelper?.showMultiSelectList(title ?: "", ArrayList(dialogData?.getItems()?.keys), selectedIndex, positiveText ?: "Done")
                dialogHelper?.showSingleSelectList(title ?: "", ArrayList(dialogData?.getItems()?.keys), selectedIndex, positiveText ?: "Done")

            }
        }.doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.
                doOnError { throwable ->
                    Log.e(TAG, throwable.message, throwable)
                    throwable.printStackTrace()
                }.share()


    }

    private fun show() {
        when {
            !editable.get() -> return
            source == null -> {
                dependencyMessage?.let { messageHelper?.showMessage(it) }
                return
            }

            else -> {
                dialogHelper?.viewModel = this
                source?.subscribe()
            }
        }
    }

    /* public void setSelectedItemsMap(@Nullable HashMap<String, Integer> selectedItemsMap) {
         if (selectedItemsMap != null)
             this.selectedItemsMap = selectedItemsMap;
         setSelectedItemsText();
         try {
             if (resultConsumer != null)
                 resultConsumer.accept(selectedItemsMap);
         } catch (Exception e) {
             Log.e(getTAG(), e.getLocalizedMessage());
         }
     }*/
    private fun setSelectedItemsText() {
        when {
            selectedItemsMap.isEmpty() -> selectedItemsText.set("select item/items")
            selectedItemsMap.size == 1 -> selectedItemsText.set(selectedItemsMap.keys.iterator().next())
            else -> selectedItemsText.set(selectedItemsMap.keys.iterator().next() + " & " + selectedItemsMap.keys.iterator().next() + " others")
        }


    }

    fun setSelectedItems(idxs: Array<Int>?) {

        messageHelper?.dismissActiveProgress()
        dialogData?.let {
            val itemList = ArrayList(it.getItems().keys)
            if (idxs == null || idxs[0] == -1)
                return
            else for (idx in idxs) {
                selectedItemsMap.put(itemList[idx], idx)
            }
            setSelectedItemsText()
            // this will be called when at least one item is selectedItems
            try {
                resultConsumer?.accept(selectedItemsMap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    fun setSelectedItem(idx: Int?) {

        messageHelper?.dismissActiveProgress()
        dialogData?.let {
            val itemList = ArrayList(it.getItems().keys)
            if (idx == null || idx == -1)
                return
            selectedItemsMap.put(itemList[idx], idx)
            setSelectedItemsText()
            // this will be called when at least one item is selectedItems
            try {
                resultConsumer?.accept(selectedItemsMap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    fun handleOkClick() {
        dismiss()
    }

    private fun dismiss() {
        when {
            !compositeDisposable.isDisposed -> compositeDisposable.dispose()
        }
    }
}
