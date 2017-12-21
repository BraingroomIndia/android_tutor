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
import com.braingroom.tutor.utils.defaultBinder
import com.braingroom.tutor.viewmodel.ViewModel
import java.util.*


class RecyclerViewAdapter(viewModelList: List<ViewModel>?, private val viewProvider: ViewProvider) : RecyclerView.Adapter<DataBindingViewHolder>() {


    private val binder: ViewModelBinder

    private val viewModelList: List<ViewModel>


    init {
        this.binder = defaultBinder
        this.viewModelList = viewModelList ?: ArrayList()
    }


    override fun getItemViewType(position: Int): Int {
        try {
            return if (viewModelList.size > position) viewProvider.getView(viewModelList[position]) else 0
        } catch (e: Exception) {
            if (e is NoSuchFieldException)
                Log.e("RecyclerViewAdapter", "No layout found corresponding to " + viewModelList[position].TAG)
            e.printStackTrace()
            if (e is NullPointerException)
                Log.e("RecyclerViewAdapter", "Null pointer error at position" + position)
        }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {

        if (viewModelList.size <= position) {
            return
        }
        holder.itemViewType
        binder.bind(holder.viewBinding, viewModelList[position])
        holder.viewBinding.executePendingBindings()
    }

    override fun onViewRecycled(holder: DataBindingViewHolder?) {
        binder.bind(holder!!.viewBinding, null)
    }

    override fun getItemCount(): Int {
        return viewModelList.size
    }


}

