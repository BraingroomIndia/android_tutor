package com.braingroom.tutor.viewmodel.activity

import android.content.Intent
import android.databinding.ObservableField
import android.text.InputType
import android.util.Log
import android.view.View
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.SignupActivity
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action
import com.braingroom.tutor.viewmodel.item.TextIconViewModel
/*
 * Created by ashketchup on 30/11/17.
 */
class SignupViewModel constructor():ViewModel(){
    val name = TextIconViewModel("",null,InputType.TYPE_CLASS_TEXT, View.VISIBLE,"Name","Enter Valid Name")
    val phone = TextIconViewModel("",null,InputType.TYPE_CLASS_PHONE, View.VISIBLE,"Phone","Enter Valid Phone Number")
    val email = TextIconViewModel("",null,InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,View.VISIBLE,"Email","Enter Valid Email")
    val password = TextIconViewModel("",null, PASSWORD_INPUT, View.VISIBLE,"Password","Enter Valid Password")
    val username= TextIconViewModel("",null,InputType.TYPE_CLASS_TEXT, View.VISIBLE,"Username","Enter Valid UserName")
    val signUpButton: CustomDrawable = CustomDrawable(R.drawable.rounded_corner_line,R.color.materialBlue)
    val onSignupClicked = Action{
        signup()
    }
    constructor(messageHelper: MessageHelper,navigator:Navigator,uiHelper:SignupActivity.UiHelper,fragmentHelper: FragmentHelper,dynamicHelper: FragmentHelper) :this(){

    }

    fun signup(){
        if(!(isValidEmail(email.text.get())&& isValidName(name.text.get()) && isValidPhone(phone.text.get()) && isValidName(username.text.get()))&&isValidPassword(password.text.get()))
        {
            if(!isValidEmail(email.text.get())) {
                email.setError(true)
            } else {
                email.setError(false)
            }
            if (!isValidName(name.text.get())) {
                name.setError(true)
            } else {
                name.setError(false)
            }
            if (!isValidPhone(phone.text.get())) {
                phone.setError(true)
            } else {
                phone.setError(false)
            }

            if (!isValidName(username.text.get())) {
                username.setError(true)
            } else {
                username.setError(false)
            }
        } else {
            email.setError(false)
            phone.setError(false)
            username.setError(false)
            name.setError(false)
            password.setError(false)
        }
    }

}