package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.req.ChangePasswordReq
import com.braingroom.tutor.view.activity.HomeActivity
import com.braingroom.tutor.view.activity.LoginActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 28/12/17.
 */
class ChangePasswordViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val oldPassword = ObservableField("")
    val newPassword = ObservableField("")
    val confirmPassword = ObservableField("")
    val onChangePasswordClicked: Action by lazy {
        Action {
            if (newPassword.get() == confirmPassword.get())
                apiService.changePassword(ChangePasswordReq.Snippet(userId, oldPassword.get(), newPassword.get())).subscribe { resp ->
                    if (resp.resCode) {
                        messageHelper.logout()
                    } else {
                        messageHelper.showMessage(resp.resMsg)
                    }
                }
            else {
                messageHelper.showMessage("Password doesn't match")
                newPassword.set("")
                confirmPassword.set("")
            }
        }
    }
}