@file:Suppress("unused")

package com.braingroom.tutor.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.braingroom.tutor.view.adapters.*
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

/*
 * Created by godara on 12/10/17.
 */


@BindingAdapter(value = *arrayOf("items", "view"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: List<ViewModel>?, viewProvider: ViewProvider?) {
    recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapter(items, it) }
}

@BindingAdapter(value = *arrayOf("items", "view"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: ReplaySubject<out ViewModel>?, viewProvider: ViewProvider?) {
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapterObservable(items, it) }
    recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
}

/*@BindingAdapter(value = *arrayOf("items", "view"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: PublishSubject<out ViewModel>, viewProvider: ViewProvider?) {
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapterReplaySubject(items, it) }
    recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
}*/


@BindingAdapter(value = *arrayOf("items", "view", "decor"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: List<ViewModel>?, viewProvider: ViewProvider?, decor: RecyclerView.ItemDecoration?) {
    recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapter(items, it) }
    recyclerView?.addItemDecoration(decor)
}


@BindingAdapter(value = *arrayOf("items", "view", "decor"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: ReplaySubject<out ViewModel>?, viewProvider: ViewProvider?, decor: RecyclerView.ItemDecoration?) {
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapterObservable(items, it) }
    recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
    recyclerView?.addItemDecoration(decor)
}


@BindingAdapter(value = *arrayOf("items", "view", "span"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: List<ViewModel>?, viewProvider: ViewProvider?, span: Int?) {
    recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, span ?: 2)
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapter(items, it) }
    recyclerView?.addItemDecoration(GridSpacingItemDecoration(span ?: 2, convertDpToPixel(5).toInt(), true))
}


@BindingAdapter(value = *arrayOf("items", "view", "span"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: ReplaySubject<out ViewModel>?, viewProvider: ViewProvider?, span: Int?) {
    recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, span ?: 2)
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapterObservable(items, it) }
    recyclerView?.addItemDecoration(GridSpacingItemDecoration(span ?: 2, convertDpToPixel(5).toInt(), true))
}


@BindingAdapter(value = *arrayOf("items", "view", "span", "decor"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: List<ViewModel>?, viewProvider: ViewProvider?, span: Int?, decor: RecyclerView.ItemDecoration?) {
    recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, span ?: 2)
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapter(items, it) }
    recyclerView?.addItemDecoration(decor)
}

@BindingAdapter(value = *arrayOf("items", "view", "span", "decor"), requireAll = true)
fun bindAdapterWithDefaultBinder(recyclerView: RecyclerView?, items: ReplaySubject<out ViewModel>?, viewProvider: ViewProvider?, span: Int?, decor: RecyclerView.ItemDecoration?) {
    recyclerView?.layoutManager = GridLayoutManager(recyclerView?.context, span ?: 2)
    viewProvider?.let { recyclerView?.adapter = RecyclerViewAdapterObservable(items, it) }
    recyclerView?.addItemDecoration(decor)
}

@BindingAdapter("paginate")
fun paginate(recyclerView: RecyclerView?, viewModel: ViewModel) {
    recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView?.getChildCount() ?: 2
            val totalItemCount = recyclerView?.layoutManager?.getItemCount() ?: 5
            val firstVisibleItemPosition = ((recyclerView?.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()

            if (dy > 0 && visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                viewModel.paginate()
            }

        }
    })
}


@BindingAdapter("adapter")
fun bindAdapter(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>?) {
    recyclerView?.adapter = adapter
}