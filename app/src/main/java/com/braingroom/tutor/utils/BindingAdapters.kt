@file:Suppress("unused")

package com.braingroom.tutor.utils

import android.databinding.BindingAdapter
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.view.adapters.RecyclerViewAdapter
import com.braingroom.tutor.view.adapters.RecyclerViewAdapterObservable
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import io.reactivex.functions.Action
import io.reactivex.subjects.ReplaySubject

/*
 * Created by godara on 12/10/17.
 */

@BindingAdapter(value = *arrayOf("items", "decor", "spanCount", "paginate", "view"), requireAll = false)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: List<RecyclerViewItem>?, decor: RecyclerView.ItemDecoration?, span: Int?, paginate: Action?, viewProvider: ViewProvider?) {
    CustomApplication.getInstance()?.refWatcher?.watch(recyclerView?.adapter)
    when (span) {
        null -> recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
        1 -> recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
        else -> recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, span)
    }
    decor?.let { recyclerView?.addItemDecoration(it) }
    recyclerView?.isNestedScrollingEnabled = false
    paginate?.let { paginateRecyclerView(recyclerView, it) }

    viewProvider?.let { bindAdapter(recyclerView, RecyclerViewAdapter(items, it)) }
}

@BindingAdapter(value = *arrayOf("items", "decor", "spanCount", "paginate", "view"), requireAll = false)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: ReplaySubject<out RecyclerViewItem>?, decor: RecyclerView.ItemDecoration?, span: Int?, paginate: Action?, viewProvider: ViewProvider?) {
    CustomApplication.getInstance()?.refWatcher?.watch(recyclerView?.adapter)
    when (span) {
        null -> recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
        1 -> recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
        else -> recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, span)
    }
    recyclerView?.isNestedScrollingEnabled = false
    decor?.let { recyclerView?.addItemDecoration(it) }
    paginate?.let { paginateRecyclerView(recyclerView, it) }

    viewProvider?.let { bindAdapter(recyclerView, RecyclerViewAdapterObservable(items, it)) }

}


fun paginateRecyclerView(recyclerView: RecyclerView?, paginate: Action?) {
    recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView?.childCount ?: 2
            val totalItemCount = recyclerView?.layoutManager?.itemCount ?: 5
            val firstVisibleItemPosition = ((recyclerView?.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
            if (dy > 0 && visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                paginate?.run()
            }
        }
    })
}


@BindingAdapter("adapter")
fun bindAdapter(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>?) {
    recyclerView?.adapter = adapter
}
