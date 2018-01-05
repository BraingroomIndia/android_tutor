@file:Suppress("USELESS_ELVIS")

package com.braingroom.tutor.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.ViewDataBinding
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputLayout
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
import android.support.v7.widget.AppCompatSpinner
import android.databinding.InverseBindingAdapter
import android.widget.*


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
                Log.e("toOnClickListener", e.message, e)
            }
        }
        else -> null
    }
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView?, imageUrl: String?) {
    if (!isEmpty(imageUrl)) {
        Log.v("setImageUri", imageUrl)
        view?.let { picasso.load(imageUrl).into(it) }
    }
}

@BindingAdapter(value = *arrayOf("android:src", "placeHolder"), requireAll = true)
fun setImageUri(view: ImageView?, imageUrl: String?, placeHolder: Int?) {
    if (!imageUrl.isNullOrBlank()) {
        Log.v("setImageUri", imageUrl + "   " + placeHolder)
        if (placeHolder != null && placeHolder != 0)
            view?.let { picasso.load(imageUrl).placeholder(placeHolder).error(placeHolder).fit().centerCrop().into(it) }
        else setImageUri(view, imageUrl)
    } else if (placeHolder != null && placeHolder != 0) view?.let { picasso.load(placeHolder).fit().centerCrop().into(it) }
}

@BindingAdapter(value = *arrayOf("android:src", "placeholder"), requireAll = true)
fun setImageUri(view: ImageView?, imageUrl: String?, placeHolder: Int) {
    if (!imageUrl.isNullOrBlank()) {
        Log.v("setImageUri", imageUrl + "   " + placeHolder)
        if (placeHolder != 0)
            view?.let { picasso.load(imageUrl).placeholder(placeHolder).error(placeHolder).fit().centerCrop().into(it) }
        else setImageUri(view, imageUrl)
    } else if (placeHolder != 0) view?.let { picasso.load(placeHolder).fit().centerCrop().into(it) }
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView?, drawable: Drawable?) {
    view?.setImageDrawable(drawable)
}

@BindingAdapter("model")
fun loadHeader(view: NavigationView, model: HomeViewModel?) {
    val binding = NavHomeHeaderBinding.inflate(LayoutInflater.from(view.context))
    binding.vm = model
    binding?.executePendingBindings()
    view.addHeaderView(binding?.root)
}

/*@BindingAdapter(value = *arrayOf("android:src", "placeHolder"), requireAll = true)
fun setImageUrl(imageView: ImageView?, url: String?, placeHolder: Int?) {
    Log.v("Binding Utils", "setImageUrl: " + url ?: "null")
    if (!url.isNullOrBlank())
        imageView?.let { picasso?.load(url)?.placeholder(placeHolder)?.error(placeHolder)?.*//*centerInside()?.resize(it.width, it.height)?.*//*into(it) }
}*/


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

/*
@BindingAdapter("android:OnEditorActionListener")
fun setOnEditorActionListener(view: TextView?, listener: TextView.OnEditorActionListener) {
    view?.setOnEditorActionListener(listener)
}
*/

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


