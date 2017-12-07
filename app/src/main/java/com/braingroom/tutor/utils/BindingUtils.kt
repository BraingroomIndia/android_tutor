package com.braingroom.tutor.utils

import android.databinding.*
import android.view.View
import com.braingroom.tutor.BR
import com.braingroom.tutor.view.adapters.ViewModelBinder
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import com.braingroom.tutor.common.CustomApplication
import android.widget.TextView


/*
 * Created by godara on 27/09/17.
 */
public val defaultBinder: ViewModelBinder by lazy {
    Log.d("Default Binder", "created")
    object : ViewModelBinder {
        override fun bind(viewDataBinding: ViewDataBinding?, viewModel: ViewModel?) {
            viewDataBinding?.setVariable(BR.vm, viewModel)
        }
    }
}
val picasso by lazy {
    CustomApplication.getInstance().appModule.picasso
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
                e.printStackTrace();
            }
        }
        else -> null
    }
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView?, drawable: Drawable?) {
    view?.setImageDrawable(drawable)
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView?, imageUrl: String?) {
    Log.d("setImageUri", imageUrl)
    view?.let { picasso.load(imageUrl).resize(it.width, it.height).into(it) }
}

@BindingAdapter(value = *arrayOf("android:drawableLeft", "android:drawableRight", "android:drawableTop", "android:drawableBottom"), requireAll = false)
fun setDrawableBottom(view: TextView?,
                      drawableLeft: Drawable?, drawableRight: Drawable?, drawableTop: Drawable?, drawableBottom: Drawable?) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        view?.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
    else
        view?.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
}

@BindingAdapter("android:background")
fun setBackground(view: View?, drawable: Drawable?) {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        view?.background = drawable
    else
        view?.setBackgroundDrawable(drawable)
}

@BindingAdapter("android:drawableTint")
fun setBackground(view: TextView?, color: Int) {
    view?.compoundDrawables?.let {
        it.forEach { drawable -> drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY) }
    }
}

@BindingAdapter("android:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
    view.error = errorMessage
}

@BindingAdapter("android:errorErrorEnabled")
fun setErrorMessage(view: TextInputLayout, errorEnabled: Boolean) {
    view.isErrorEnabled = errorEnabled
}


