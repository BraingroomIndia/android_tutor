package com.braingroom.tutor.view.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.LoadingViewModel
import com.braingroom.tutor.viewmodel.item.RemoveLoadingViewModel

import java.util.ArrayList
import java.util.HashMap

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import com.braingroom.tutor.utils.defaultBinder
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RefreshViewModel
import io.reactivex.functions.Consumer
import io.reactivex.subjects.ReplaySubject

/*
 * Created by godara on 06/11/17.
 */

class RecyclerViewAdapterReplaySubject(replaySubjectViewModel: ReplaySubject<out ViewModel>?, private val viewProvider: ViewProvider) : RecyclerView.Adapter<DataBindingViewHolder>() {
    private val latestViewModels = ArrayList<ViewModel>(0)
    private val binder: ViewModelBinder = defaultBinder
    private val source: Observable<out ViewModel?>?
    private val subscriptions = HashMap<RecyclerView.AdapterDataObserver, Disposable>()
    private val TAG = this.javaClass.simpleName

    init {
        source = replaySubjectViewModel?.replay()?.subscribeOn(Schedulers.computation())?.
                doOnNext { viewModel ->
                    val iterator = latestViewModels.listIterator(latestViewModels.size)
                    viewModel?.let {
                        when (it) {
                            is RemoveLoadingViewModel ->
                                while (iterator.hasPrevious() && iterator.previous() is LoadingViewModel) iterator.remove()
                            is RefreshViewModel ->
                                latestViewModels.clear()
                            else ->
                                iterator.add(viewModel)
                        }

                    }

                }?.share()
    }


    override fun getItemViewType(position: Int): Int {
        return viewProvider.getView(latestViewModels[position])
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
        source?.let {
            subscriptions.put(observer, it.observeOn(AndroidSchedulers.mainThread()).subscribe({ viewModel ->
                if (viewModel is NotifyDataSetChanged && !latestViewModels.isEmpty())
                    notifyDataSetChanged()
            }, { throwable ->
                Log.e(TAG, throwable.localizedMessage, throwable)
            }))
        }
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        subscriptions.remove(observer)?.let { if (!it.isDisposed) it.dispose() }
    }


}
