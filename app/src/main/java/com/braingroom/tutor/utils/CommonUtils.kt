package com.braingroom.tutor.utils

import android.content.res.Resources
import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.net.MalformedURLException

import java.net.URL
import kotlin.collections.HashMap



/*
 * Created by godara on 10/10/17.
 */



fun <T> toObservable(field: ObservableField<T>): Observable<T> {


    when {
        field.get() == null -> throw NullPointerException()
        else -> return Observable.create(ObservableOnSubscribe<T> { e ->
            e.onNext(field.get()!!)
            val callback = object : OnPropertyChangedCallback() {
                override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                    e.onNext(field.get()!!)
                    Log.v("onPropertyChanged", "cancel: " + field.toString())
                }
            }
            field.addOnPropertyChangedCallback(callback)
            e.setCancellable {
                field.removeOnPropertyChangedCallback(callback)
                Log.v("removeOnProperty", "cancel: " + field.toString())
            }
        })
    }
}


//  This method converts device density independent pixels to specific pixels .
fun convertDpToPixel(dp: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertDpToPixel(dp: Int): Float {
    val metrics = Resources.getSystem().displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


//  This method converts device specific pixels to density independent pixels.
fun convertPixelsToDp(px: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun fromHtml(html: String?): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(html ?: "", Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html ?: "")
    }


}


fun getRandomNumberInRange(min: Int, max: Int): Int {

    if (min >= max) {
        throw IllegalArgumentException("max must be greater than min")
    }

    val r = Random()
    return r.nextInt(max - min + 1) + min
}

fun String?.isValidEmail(): Boolean = !this.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

// Checks if password is lesser than 8 charachters

fun isValidPassword(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        target.length > 4
    }
}

fun isValidPhone(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        android.util.Patterns.PHONE.matcher(target).matches()
    }
}

fun toString(map:HashMap<String,Int>):String{
    var result="";
    var list:MutableList<Int> = mutableListOf()
    var array = map.values.iterator()
    if(array.hasNext()){
        result+=array.next()
        while (array.hasNext()){
            result+=",";
            result+=array.next()
        }
        return result
    }
    return ""
}

fun isValidName(target: CharSequence?): Boolean {
    if (isEmpty(target.toString()))
        return false
    else
        return if (target == null) {
            false
        } else {
            !target.contains("[^a-zA-Z]")
        }
}

fun getThumbnail(url: String): String {
    return "http://img.youtube.com/vi/" + extractYoutubeId(url) + "/0.jpg"
}

public fun getVideo(video: String?): String {
    if (video == null) return "";
    try {
        return video.substring(video.lastIndexOf("/") + 1);
    } catch (iobe: IndexOutOfBoundsException) {
        return "";
    }
}

@Throws(MalformedURLException::class)
fun extractYoutubeId(url: String?): String? = (url?.split("/".toRegex())?.toTypedArray())?.last()

fun isEmpty(target: String?): Boolean = target.isNullOrBlank()
fun isEmpty(target: Any?): Boolean = target == null
fun getNonNull(target: String?) = target ?: "";



