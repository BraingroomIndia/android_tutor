package com.braingroom.tutor.view.adapters

/*
 * Created by godara on 12/10/17.
 */


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.defaultBinder
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.EmptyItemViewModel
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import timber.log.Timber
import java.util.*


class RecyclerViewAdapter(viewModelList: List<RecyclerViewItem>?, private val viewProvider: ViewProvider) : RecyclerView.Adapter<DataBindingViewHolder>() {


    private val binder: ViewModelBinder

    private val viewModelList: List<RecyclerViewItem>
    val TAG: String
        get() = this::class.java.simpleName ?: ""


    init {
        this.binder = defaultBinder
        this.viewModelList = viewModelList ?: ArrayList()
    }


    override fun getItemViewType(position: Int): Int {
        if (viewModelList[position] is EmptyItemViewModel)
            return R.layout.item_empty_view
        try {
            return viewProvider.getView(viewModelList[position])
        } catch (e: Exception) {
            when (e) {
                is NoSuchFieldException -> Timber.tag(TAG).e(e, "No layout found corresponding to " + viewModelList[position].TAG)
                is NullPointerException -> Timber.tag(TAG).e(e, "Null pointer error at position" + position)
                else -> Timber.tag(TAG).e(e, "No Idea")
            }
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent?.context), viewType, parent, false))
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder?, position: Int) {
        binder.bindRecyclerView(holder?.viewBinding, viewModelList[position])
        holder?.viewBinding?.executePendingBindings()
    }

    override fun onViewRecycled(holder: DataBindingViewHolder?) {
        binder.bindRecyclerView(holder?.viewBinding, null)
        holder?.viewBinding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return viewModelList.size
    }


}

