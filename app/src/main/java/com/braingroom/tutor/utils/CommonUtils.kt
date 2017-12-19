package com.braingroom.tutor.utils

import android.content.res.Resources
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import java.util.*
import io.reactivex.Observable;
import android.databinding.Observable.OnPropertyChangedCallback
import io.reactivex.ObservableOnSubscribe
import android.databinding.ObservableField
import android.util.Log
import java.net.MalformedURLException
import java.net.URL


/*
 * Created by godara on 10/10/17.
 */



fun <T> toObservable(field: ObservableField<T>): Observable<T> {

    return Observable.create(ObservableOnSubscribe<T> { e ->
        e.onNext(field.get()!!)
        val callback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                e.onNext(field.get()!!)
                Log.d("onPropertyChanged", "cancel: " + field.toString())
            }
        }
        field.addOnPropertyChangedCallback(callback)
        e.setCancellable {
            field.removeOnPropertyChangedCallback(callback)
            Log.d("removeOnProperty", "cancel: " + field.toString())
        }
    })
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

fun isValidEmail(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

// Checks if password is lesser than 8 charachters

fun isValidPassword(target: CharSequence?):Boolean{
    return if (target== null){
        false
    }else{
        target.length>4
    }
}

fun isValidPhone(target: CharSequence?):Boolean{
    return if(target==null){
        false
    }else{
        android.util.Patterns.PHONE.matcher(target).matches()
    }
}

fun isValidName(target: CharSequence?):Boolean{
    if(isEmpty(target.toString()))
        return false
    else
    return if(target==null){
        false
    }else{
        !target.contains("[^a-zA-Z]")
    }
}
fun getThumbnail(url:String):String{
    return "http://img.youtube.com/vi/"+extractYoutubeId(url)+"/0.jpg"
}

public fun getVideo(video:String?):String {
    if (video == null) return "";
    try {
        return video.substring(video.lastIndexOf("/") + 1);
    } catch (iobe:IndexOutOfBoundsException ) {
        return "";
    }
}
@Throws(MalformedURLException::class)
fun extractYoutubeId(url: String): String? {
    val query = url
    val param = query.split("/".toRegex()).toTypedArray()
    var id: String? = null
    return param[param.lastIndex]
}

fun isEmpty(target: String?): Boolean = target.isNullOrBlank()
fun isEmpty(target: Object?): Boolean = target == null
fun getNonNull(target: String?) = target ?: "";



