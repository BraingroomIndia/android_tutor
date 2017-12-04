package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import android.text.InputType
import android.util.Log
import android.view.View
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action
import com.braingroom.tutor.viewmodel.item.TextIconViewModel


/**
 * Created by ashketchup on 30/11/17.
 */
class SignupViewModel:ViewModel(){
    val name = TextIconViewModel("",null,InputType.TYPE_CLASS_TEXT, View.VISIBLE,"Enter Valid Name","Name")
    val phone = TextIconViewModel("",null,InputType.TYPE_CLASS_PHONE, View.VISIBLE,"Enter Valid Phone Number","Phone")
    val email = TextIconViewModel("",null,InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,View.VISIBLE,"Enter Valid Name","Email")
    val password = TextIconViewModel("",null, PASSWORD_INPUT, View.VISIBLE,"Enter Valid Password","Password")
    val username= TextIconViewModel("",null,InputType.TYPE_CLASS_TEXT, View.VISIBLE,"Enter Valid UserName","Username")
    val signUpButton: CustomDrawable = CustomDrawable(R.drawable.rounded_corner_line,R.color.materialBlue)
    val onSignupClicked = Action{
        signup()
    }
    fun signup(){
        if((isValidEmail(email.text.get())&& isValidName(name.text.get()) && isValidPhone(phone.text.get()) && isValidName(username.text.get())))
        {

        }
    }

}