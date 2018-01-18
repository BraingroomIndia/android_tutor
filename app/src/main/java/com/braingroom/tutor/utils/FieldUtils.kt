package com.braingroom.tutor.utils

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import android.util.Log
import io.reactivex.Observable


object FieldUtils {
    fun <T> toObservable(field: ObservableField<T>): Observable<T> {

        when {
            field.get() == null -> throw NullPointerException()
            else -> return Observable.create { e ->
                e.onNext(field.get()!!)
                val callback = object : OnPropertyChangedCallback() {
                    override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                        e.onNext(field.get()!!)
                        Log.v("onPropertyChanged", "cancel: " + field.toString())
                    }
                }
                field.addOnPropertyChangedCallback(callback)
                e.setCancellable {
                    field.removeOnPropertyChangedCallback(callback)
                    Log.e("removeOnProperty", "cancel: " + field.toString())
                }
            }
        }
    }

    /**
     * A convenient wrapper for `ReadOnlyField#create(Observable)`
     *
     * @return DataBinding field created from the specified Observable
     */
    fun <T> toField(observable: Observable<T>): ReadOnlyField<T> {
        return ReadOnlyField.create(observable)
    }
}
