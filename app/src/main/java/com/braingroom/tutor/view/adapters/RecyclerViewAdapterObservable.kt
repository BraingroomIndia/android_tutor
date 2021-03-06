package com.braingroom.tutor.view.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import com.braingroom.tutor.viewmodel.ViewModel

import java.util.ArrayList
import java.util.HashMap

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import com.braingroom.tutor.utils.defaultBinder
import com.braingroom.tutor.viewmodel.item.LoadingViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RefreshViewModel
import com.braingroom.tutor.viewmodel.item.RemoveLoadingViewModel
import io.reactivex.subjects.ReplaySubject


class RecyclerViewAdapterObservable(observableViewModels: ReplaySubject<out ViewModel>?,
                                    private val viewProvider: ViewProvider) : RecyclerView.Adapter<DataBindingViewHolder>() {
    private val latestViewModels = ArrayList<ViewModel>(0)
    private val binder: ViewModelBinder = defaultBinder
    private val source: Observable<out ViewModel>?
    private val subscriptions = HashMap<RecyclerView.AdapterDataObserver, Disposable>()
    public val TAG: String
        get() = this::class.java.simpleName ?: ""

    init {
        source = observableViewModels?.repeat()?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())?.
                doOnNext { viewModel ->
                    //                    Log.d(TAG, "doOnNext called")
                    viewModel?.let {
                        val iterator = latestViewModels.listIterator(latestViewModels.size)
                        when (it) {
                            is RemoveLoadingViewModel -> {
                                Log.d(TAG, "Removing Loading Items")
                                while (iterator.hasPrevious() && iterator.previous() is LoadingViewModel) iterator.remove()
                            }
                            is RefreshViewModel -> {
//                                Log.d(TAG, "Removing All Items")
                                latestViewModels.clear()
                            }
                            is NotifyDataSetChanged -> {
                                if (!latestViewModels.isEmpty()) {
                                    notifyDataSetChanged()
                                    Log.d(TAG, "Updating UI")
                                } else {
                                    Log.d(TAG, "No items to Update UI")
                                }
                            }
                            else -> {
                                Log.d(TAG, "Added Actual items Named " )
                                iterator.add(it)
                            }
                        }

                    }
                }?.doOnSubscribe { Log.d(TAG, "Subscribed") }?.share()
    }


    override fun getItemViewType(position: Int): Int {
        try {
            return if (latestViewModels.size > position) viewProvider.getView(latestViewModels[position]) else 0
        } catch (e: Exception) {
            if (e is NoSuchFieldException)
                Log.d(TAG, "No layout found corresponding to " + latestViewModels[position].TAG)
            e.printStackTrace()
            if (e is NullPointerException)
                Log.d(TAG, "Null pointer error at position" + position)
        }
        return 0;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent?.context), viewType, parent, false))
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder?, position: Int) {
        binder.bind(holder?.viewBinding, latestViewModels[position])
        holder?.viewBinding?.executePendingBindings()
    }

    override fun onViewRecycled(holder: DataBindingViewHolder?) {
        binder.bind(holder?.viewBinding, null)
        holder?.viewBinding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return latestViewModels.size
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        source?.let { it.subscribe()?.let { subscriptions.put(observer, it) } }
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        subscriptions.remove(observer)?.let { if (!it.isDisposed) it.dispose() }
    }


}