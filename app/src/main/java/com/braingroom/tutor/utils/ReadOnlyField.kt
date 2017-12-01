package com.braingroom.tutor.utils

import android.databinding.ObservableField
import android.util.Log

import java.util.HashMap

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


class ReadOnlyField<T> private constructor(source: Observable<T>) : ObservableField<T>() {
    private val source: Observable<T>
    private val subscriptions = HashMap<android.databinding.Observable.OnPropertyChangedCallback, Disposable>()

    init {
        this.source = source.doOnNext { t -> super@ReadOnlyField.set(t) }.doOnError { throwable -> Log.e("ReadOnlyField", "onError in source observable", throwable) }.onErrorResumeNext(Observable.empty()).share()
    }


    @Deprecated("Setter of ReadOnlyField does nothing. Merge with the source Observable instead.\n" +
            "      See <a href=\"https://github.com/manas-chaudhari/android-mvvm/tree/master/Documentation/ObservablesAndSetters.md\">Documentation/ObservablesAndSetters.md</a>")
    override fun set(value: T) {
    }

    @Synchronized override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)
        subscriptions.put(callback, source.subscribe())
    }

    @Synchronized override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)
        val subscription = subscriptions.remove(callback)
        if (subscription != null && !subscription.isDisposed) {
            subscription.dispose()
        }
    }

    companion object {

        fun <U> create(source: Observable<U>): ReadOnlyField<U> {
            return ReadOnlyField(source)
        }
    }
}
