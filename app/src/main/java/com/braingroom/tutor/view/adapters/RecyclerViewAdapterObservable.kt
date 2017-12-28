package com.braingroom.tutor.view.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.defaultBinder
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import java.util.*


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
                                Log.v(TAG, "Removing Loading Items")
                                while (iterator.hasPrevious() && iterator.previous() is LoadingViewModel) iterator.remove()
                            }
                            is RefreshViewModel -> {
                                Log.v(TAG, "Removing All Items")
                                latestViewModels.clear()
                            }
                            is NotifyDataSetChanged -> {

                            }
                            is LoadingViewModel -> {
                                Log.v(TAG, "Added Loading items ")
                                iterator.add(it)
                            }
                            else -> {
                                Log.v(TAG, "Added Actual items Named " + it.TAG)
                                iterator.add(it)
                            }
                        }

                    }
                }?.doOnSubscribe { Log.v(TAG, "Subscribed") }?.share()
    }


    override fun getItemViewType(position: Int): Int {
        if (latestViewModels[position] is EmptyItemViewModel)
            return R.layout.item_empty_view
        try {
            return if (latestViewModels.size > position) viewProvider.getView(latestViewModels[position]) else 0
        } catch (e: Exception) {
            if (e is NoSuchFieldException)
                Log.e(TAG, "No layout found corresponding to " + latestViewModels[position].TAG)
            e.printStackTrace()
            if (e is NullPointerException)
                Log.e(TAG, "Null pointer error at position" + position)
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
        source?.let {
            it.subscribe({ viewModel ->
                viewModel?.let {
                    when (it) {
                        is NotifyDataSetChanged -> {
                            if (!latestViewModels.isEmpty()) {
                                notifyDataSetChanged()
                                Log.v(TAG, "Updating UI")
                            } else {
                                Log.v(TAG, "No items to Update UI")
                            }
                        }
                        else -> {
                        }
                    }
                }
            })?.let { subscriptions.put(observer, it) }
        }
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        subscriptions.remove(observer)?.let { if (!it.isDisposed) it.dispose() }
    }


}