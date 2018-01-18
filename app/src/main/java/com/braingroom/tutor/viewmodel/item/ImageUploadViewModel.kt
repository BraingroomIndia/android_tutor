package com.braingroom.tutor.viewmodel.item

import android.content.Intent
import android.databinding.ObservableField
import android.util.Log

import com.braingroom.tutor.viewmodel.ViewModel

import io.reactivex.functions.Action

import android.app.Activity.RESULT_OK
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.utils.getMimeType
import com.braingroom.tutor.utils.getPath

/**
 * Created by godara on 03/01/18.
 */

class ImageUploadViewModel : ViewModel {

    val placeHolder: Int
    val remoteAddress: ObservableField<String>
    val onUploadClicked: Action
    val requestCode: Int

    constructor(helperFactory: HelperFactory, placeHolder: Int, remoteAddress: String, requestCode: Int) : super(helperFactory) {
        this.requestCode = requestCode
        this.placeHolder = placeHolder
        this.remoteAddress = ObservableField(remoteAddress)
        onUploadClicked = Action {
            navigator.launchImageChooserActivity(requestCode)
        }
    }

    constructor(helperFactory: HelperFactory, remoteAddress: String, requestCode: Int) : super(helperFactory) {
        this.requestCode = requestCode
        this.placeHolder = 0
        this.remoteAddress = ObservableField(remoteAddress)
        onUploadClicked = Action {
            navigator.launchImageChooserActivity(requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode
                && resultCode == RESULT_OK && data != null && data.data != null) {
            val fileUri = data.data
            val filePath = getPath(fileUri!!)
            val fileType = getMimeType(fileUri)
            if (filePath == null || fileType == null) {
                messageHelper.showDismissInfo("", "Unable to upload", "Okay")
                return
            }
            messageHelper.showProgressDialog("Wait", "uploading...")
            apiService.uploadImage(filePath, fileType).subscribe { resp ->
                Log.d(TAG, resp.data.url)
                messageHelper.dismissActiveProgress()
                messageHelper.showMessage(resp.resMsg)
                if (resp.resCode)
                    remoteAddress.set(resp.data.url)

            }

        }
    }

}