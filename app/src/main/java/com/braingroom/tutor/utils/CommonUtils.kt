@file:Suppress("unused")

package com.braingroom.tutor.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.MimeTypeMap
import com.braingroom.tutor.common.CustomApplication
import io.reactivex.Observable
import java.io.File
import io.reactivex.ObservableOnSubscribe
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


/*
 * Created by godara on 10/10/17.
 */



fun <T> toObservable(field: ObservableField<T>): Observable<T> {


    when {
        field.get() == null -> throw NullPointerException()
        else -> return Observable.create({ e ->
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

fun String?.isValidPhone(): Boolean = !this.isNullOrBlank() && android.util.Patterns.PHONE.matcher(this).matches()
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

fun HashMap<Int, String>.getId(): String {
    var result = ""
    val array = this.keys.iterator()
    if (array.hasNext()) {
        result += array.next()
        while (array.hasNext()) {
            result += ","
            result += array.next()
        }
        return result
    }
    return ""
}

fun isValidName(target: CharSequence?): Boolean {
    return when {
        target.isNullOrBlank() -> false
        else -> !target!!.contains("[^a-zA-Z]")
    }
}

fun getThumbnail(url: String): String {
    return "http://img.youtube.com/vi/" + extractYoutubeId(url) + "/0.jpg"
}

fun getEmbeddedYoutubeUrl(url: String) = "https://www.youtube.com/embed/" + extractYoutubeId(url)


val youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/"
val videoIdRegex = arrayOf("\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)")

fun extractYoutubeId(url: String): String {
    val youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url)

    return videoIdRegex
            .map { Pattern.compile(it) }
            .map { it.matcher(youTubeLinkWithoutProtocolAndDomain) }
            .firstOrNull { it.find() }
            ?.group(1) ?: ""
}

private fun youTubeLinkWithoutProtocolAndDomain(url: String): String {
    val compiledPattern = Pattern.compile(youTubeUrlRegEx)
    val matcher = compiledPattern.matcher(url)

    return if (matcher.find()) {
        url.replace(matcher.group(), "")
    } else url
}

fun getHumanDate(timeStamp: String): String {

    try {
        val time = Integer.valueOf(timeStamp)!!.toLong()
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(time * 1000)
        return sdf.format(netDate)
    } catch (ex: Exception) {
        return "xx"
    }

}

fun getDate(timeStamp: Timestamp): String {

    try {

        val sdf = SimpleDateFormat("dd")
        val netDate = Date(timeStamp.time)
        return sdf.format(netDate)
    } catch (ex: Exception) {
        return "xx"
    }

}

fun getDay(timeStamp: Timestamp): String {

    try {

        val sdf = SimpleDateFormat("EEEE")
        val netDate = Date(timeStamp.time)
        return sdf.format(netDate)
    } catch (ex: Exception) {
        return "xx"
    }

}

fun getMonth(timeStamp: Timestamp): String {

    try {

        val sdf = SimpleDateFormat("MMM")
        val netDate = Date(timeStamp.time)
        return sdf.format(netDate)
    } catch (ex: Exception) {
        return "xx"
    }

}

fun getYear(timeStamp: Timestamp): String {

    try {

        val sdf = SimpleDateFormat("YYYY")
        val netDate = Date(timeStamp.time)
        return sdf.format(netDate)
    } catch (ex: Exception) {
        return "xx"
    }

}


fun isEmpty(target: String?): Boolean = target.isNullOrBlank()
fun isEmpty(target: Any?): Boolean = target == null
fun getNonNull(target: String?) = target ?: ""
fun getNonNull(target: Int?) = target ?: -1
fun getNonNull(target: Float?) = target ?: -1

@SuppressLint("NewApi")
fun getPath(uri: Uri): String? {

    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    val context = CustomApplication.getInstance()
    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }


        } else if (isDownloadsDocument(uri)) {

            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            var contentUri: Uri? = null
            when (type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            return getDataColumn(context, contentUri, selection, selectionArgs)
        }// MediaProvider
        // DownloadsProvider
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        return getDataColumn(context, uri, null, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }// File
    // MediaStore (and general)

    return null
}

/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param context       The context.
 * @param uri           The Uri to query.
 * @param selection     (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 */
fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                  selectionArgs: Array<String>?): String? {

    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } finally {
        if (cursor != null)
            cursor.close()
    }
    return null
}


fun getMimeType(uri: Uri): String? {
    val extension: String?
    val context = CustomApplication.getInstance()
    //Check uri format to avoid null
    if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        //If scheme is a content
        val mime = MimeTypeMap.getSingleton()
        extension = mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
    } else {
        //If scheme is a File
        //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
        extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())

    }

    return extension
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun max(vararg n: Number): Number {
    var i = 0
    var max = n[i]

    while (++i < n.size)
        if (n[i].toDouble() > max.toDouble())
            max = n[i]

    return max
}
