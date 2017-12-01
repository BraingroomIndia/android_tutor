package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.CustomDrawable
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action
import com.braingroom.tutor.utils.isValidEmail
import com.braingroom.tutor.utils.isValidName
import com.braingroom.tutor.utils.isValidPhone


/**
 * Created by ashketchup on 30/11/17.
 */
class SignupViewModel:ViewModel(){
    val name = ObservableField<String>("")
    val phone = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")
    val username= ObservableField<String>("")
    val loginButton: CustomDrawable = CustomDrawable(R.drawable.rounded_corner_line,R.color.material_deeporange600)
    val onSignupClicked = Action{
        signup()
    }
    fun signup(){
        Log.d("SignupExample",""+(isValidEmail(email.get())&& isValidName(name.get()) && isValidPhone(phone.get()) && isValidName(username.get())))
    }

}