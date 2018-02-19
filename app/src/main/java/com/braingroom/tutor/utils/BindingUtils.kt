package com.braingroom.tutor.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.ViewDataBinding
import android.graphics.drawable.Drawable
import android.support.design.widget.NavigationView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.braingroom.tutor.BR
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.databinding.NavHomeHeaderBinding
import com.braingroom.tutor.view.adapters.ViewModelBinder
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.HomeViewModel
import io.reactivex.functions.Action
import android.databinding.InverseBindingListener
import android.databinding.InverseBindingAdapter
import android.support.annotation.DrawableRes
import android.widget.*
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat.setTint
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import com.braingroom.tutor.common.modules.GlideApp
import timber.log.Timber


val TAG = "BindingUti"
val defaultBinder: ViewModelBinder by lazy {
    Timber.tag(TAG).v("created")

    object : ViewModelBinder {
        override fun bindRecyclerView(viewDataBinding: ViewDataBinding?, viewModel: RecyclerViewItem?) {
            viewDataBinding?.setVariable(BR.vm, viewModel)
        }

        override fun bind(viewDataBinding: ViewDataBinding?, viewModel: ViewModel?) {
            viewDataBinding?.setVariable(BR.vm, viewModel)
        }
    }
}


@BindingAdapter("android:visibility")
fun bindVisibility(view: View?, visible: Boolean?) = visible?.let { view?.visibility = if (it) View.VISIBLE else View.GONE }

@BindingConversion
fun toOnClickListener(listener: Action?): View.OnClickListener? {
    return when {
        listener != null -> View.OnClickListener {
            try {
                listener.run()
            } catch (e: Exception) {
                Timber.tag("toOnClickListener").e(e, e.message)
            }
        }
        else -> null
    }
}


@BindingAdapter(value = *arrayOf("src", "placeHolder"), requireAll = false)
fun setImageUri(view: ImageView?, imageUrl: String?, placeHolder: Int?) {
    CustomApplication.getInstance()?.refWatcher?.watch(view?.drawable)
    Timber.tag("setImageUri").d(imageUrl)
    if (!imageUrl.isNullOrBlank()) {
        if (placeHolder != null && placeHolder != 0)
            view?.let { GlideApp.with(it).load(imageUrl).placeholder(placeHolder).error(placeHolder).fitCenter().centerCrop().into(it) }
        else view?.let { GlideApp.with(it).load(imageUrl).into(it) }
    } else if (placeHolder != null && placeHolder != 0) view?.let { GlideApp.with(it).load(placeHolder).fitCenter().centerCrop().into(it) }
}


@BindingAdapter("model")
fun loadHeader(view: NavigationView, model: HomeViewModel?) {
    val binding = NavHomeHeaderBinding.inflate(LayoutInflater.from(view.context))
    binding.vm = model
    binding.executePendingBindings()
    view.addHeaderView(binding.root)
}


@BindingAdapter(value = *arrayOf("android:drawableLeft", "android:drawableRight", "android:drawableTop", "android:drawableBottom"), requireAll = false)
fun setCompoundDrawable(view: TextView?,
                        drawableLeft: Drawable?, drawableRight: Drawable?, drawableTop: Drawable?, drawableBottom: Drawable?) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        view?.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
    else
        view?.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)

}


@BindingAdapter("background")
fun setBackground(view: View?, @DrawableRes res: Int) {
    if (res != 0)
        view?.let { ViewCompat.setBackground(view, ContextCompat.getDrawable(view.context, res)) }
}

@BindingAdapter("errorText")
fun setErrorMessage(view: EditText, errorMessage: String?) {
    when (errorMessage.isNullOrBlank()) {
        true -> view.error = null
        else -> view.error = errorMessage
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = *arrayOf("selectedValue", "selectedValueAttrChanged"), requireAll = false)
fun bindSpinnerData(pAppCompatSpinner: Spinner, newSelectedValue: String?, newTextAttrChanged: InverseBindingListener) {
    pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
    if (newSelectedValue != null) {
        val pos = (pAppCompatSpinner.adapter as ArrayAdapter<String>).getPosition(newSelectedValue)
        pAppCompatSpinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun getSelectedValue(pAppCompatSpinner: Spinner): String {
    return pAppCompatSpinner.selectedItem as String
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("colorTint")
fun setColorTint(view: ImageView, @ColorRes color: Int) {
    if (color != 0)
        setTint(view.drawable, ContextCompat.getColor(view.context, color))
}

@BindingAdapter("nestedScrollingEnabled")
fun setNestedScrollingEnabled(view: RecyclerView, isNestedScrollingEnabled: Boolean) {
    ViewCompat.setNestedScrollingEnabled(view, isNestedScrollingEnabled)
}

@BindingAdapter("paginate")
fun paginateInNestedScrollView(nestedScrollView: NestedScrollView?, paginate: Action?) {
    nestedScrollView?.viewTreeObserver?.addOnScrollChangedListener {
        val view = nestedScrollView.getChildAt(nestedScrollView.childCount - 1) as View
        val diff = view.bottom - (nestedScrollView.height + nestedScrollView.scrollY)
        if (diff == 0) {
            paginate?.run()
        }
    }
}

