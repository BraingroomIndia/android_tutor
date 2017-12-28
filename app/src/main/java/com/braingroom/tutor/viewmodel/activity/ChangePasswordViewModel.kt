package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import com.braingroom.tutor.model.req.ChangePasswordReq
import com.braingroom.tutor.view.activity.HomeActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 28/12/17.
 */
class ChangePasswordViewModel: ViewModel(){
    val oldPassword = ObservableField("")
    val newPassword = ObservableField("")
    val confirmPassword = ObservableField("")
    val onChangePasswordClicked: Action by lazy {
        Action {
            if(newPassword.get().equals(confirmPassword.get()))
            apiService.changePassword(ChangePasswordReq.Snippet(userId,oldPassword.get(), newPassword.get())).subscribe{ resp ->
                if(resp.resCode!=false){
                    navigator?.navigateActivity(HomeActivity::class.java)
                }else{
                    //TODO change once api has been changed
                    navigator?.navigateActivity(HomeActivity::class.java)
                }
            }
            else{
                newPassword.set("")
                confirmPassword.set("")
            }
        }
    }
}