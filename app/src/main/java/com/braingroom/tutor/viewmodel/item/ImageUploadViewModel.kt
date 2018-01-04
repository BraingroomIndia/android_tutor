package com.braingroom.tutor.viewmodel.item

import android.content.Intent
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.net.Uri
import android.util.Log

import com.braingroom.tutor.model.resp.UploadMediaResp
import com.braingroom.tutor.viewmodel.ViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

import android.app.Activity.RESULT_OK
import com.braingroom.tutor.utils.getMimeType
import com.braingroom.tutor.utils.getPath
import com.braingroom.tutor.utils.IMAGE_UPLOAD_REQ

/**
 * Created by godara on 03/01/18.
 */

class ImageUploadViewModel : ViewModel {

    val placeHolder: Int
    val remoteAddress: ObservableField<String>
    val onUploadClicked: Action

    constructor(placeHolder: Int, remoteAddress: String) {
        this.placeHolder = placeHolder
        this.remoteAddress = ObservableField(remoteAddress)
        onUploadClicked = Action {
            navigator?.launchImageChooserActivity(IMAGE_UPLOAD_REQ)
        }
    }

    constructor(remoteAddress: String) {
        this.placeHolder = 0
        this.remoteAddress = ObservableField(remoteAddress)
        onUploadClicked = Action {
            navigator?.launchImageChooserActivity(IMAGE_UPLOAD_REQ)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_UPLOAD_REQ
                && resultCode == RESULT_OK && data != null && data.data != null) {
            val fileUri = data.data
            val filePath = getPath(fileUri!!)
            val fileType = getMimeType(fileUri)
            if (filePath == null || fileType == null) {
                messageHelper?.showDismissInfo("", "Unable to upload")
                return
            }
            messageHelper?.showProgressDialog("Wait", "uploading...")
            apiService.uploadImage(filePath, fileType).subscribe { resp -> Log.d(TAG, resp.data.url) }

        }
    }

}