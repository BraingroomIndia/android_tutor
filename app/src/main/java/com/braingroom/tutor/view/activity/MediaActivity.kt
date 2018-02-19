package com.braingroom.tutor.view.activity

import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog
import com.braingroom.tutor.R
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.utils.getEmbeddedYoutubeUrl
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.MediaViewModel

/*
 * Created by ashketchup on 6/12/17.
 */
class MediaActivity : Activity() {


    override val vm: MediaViewModel by lazy {
        MediaViewModel(helperFactory, {
            MaterialDialog.Builder(this)
                    .title("Upload Video")
                    .content("Please enter youtube url")
                    .inputType(InputType.TYPE_TEXT_VARIATION_URI)
                    .inputRange(11, 500)
                    .positiveText("SUBMIT")
                    .input("Email", "", false) { dialog, input ->
                        messageHelper.showProgressDialog("Wait", "Uploading.")
                        apiService.saveMedia(getEmbeddedYoutubeUrl(input.toString()), "video").subscribe { messageHelper.showMessage(it.resMsg) }

                    }.build().show()
        })
    }

    override val layoutId: Int = R.layout.activity_media
}