package com.braingroom.tutor.viewmodel.activity

import android.content.Intent
import android.databinding.ObservableField
import android.text.InputType
import android.util.Log
import android.view.View
import com.braingroom.tutor.R
import com.braingroom.tutor.model.data.InputTypeEnum
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.SignupActivity
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel
import io.reactivex.functions.Action
import com.braingroom.tutor.viewmodel.item.TextIconViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/*
 * Created by ashketchup on 30/11/17.
 */
class SignupViewModel constructor() : ViewModel() {
    val name by lazy {
        TextIconViewModel("", null, InputTypeEnum.Text, View.VISIBLE, "Name", "Enter Valid Name")
    }
    val phone by lazy {
        TextIconViewModel("", null, InputTypeEnum.Number, View.VISIBLE, "Phone", "Enter Valid Phone Number")
    }
    val email by lazy {
        TextIconViewModel("", null, InputTypeEnum.Text, View.VISIBLE, "Email", "Enter Valid Email")
    }
    val password by lazy {
        TextIconViewModel("", null, InputTypeEnum.Password, View.VISIBLE, "Password", "Enter Valid Password")
    }
    val confirmPassword by lazy {
        TextIconViewModel("", null, InputTypeEnum.Password, View.VISIBLE, "Confirm Password", "Password doesn't match")

    }
    val referralCode by lazy {
        TextIconViewModel("", null, InputTypeEnum.Text, View.VISIBLE, "Referral Code (Optional)", "")
    }
    val signUpButton by lazy {
        CustomDrawable(R.drawable.rounded_corner_line, R.color.materialBlue)
    }


    val countryVm by lazy {
        ListDialogViewModel("Interest", apiService.getCategories().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->

            val list: ListDialogData = ListDialogData(LinkedHashMap())
            for (snippet in resp.data)
                list.getItems().put(snippet.categoryName, snippet.id)
            list

        }, HashMap(), true, Consumer { /*TODO*/ },"", "Done" )
    }
    val categoryVm by lazy {
        ListDialogViewModel("Interest", apiService.getCategories().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->

            val list: ListDialogData = ListDialogData(LinkedHashMap())
            for (snippet in resp.data)
                list.getItems().put(snippet.categoryName, snippet.id)
            list

        }, HashMap(), true, Consumer { /*TODO*/ },"", "Done" )
    }
    val onSignupClicked by lazy {
        Action {
            signup()
        }
    }

    constructor(messageHelper: MessageHelper, navigator: Navigator, uiHelper: SignupActivity.UiHelper, fragmentHelper: FragmentHelper, dynamicHelper: FragmentHelper) : this() {

    }

    fun signup() {
        if (!(isValidEmail(email.text.get()) && isValidName(name.text.get()) && isValidPhone(phone.text.get()) && isValidName(confirmPassword.text.get())) && isValidPassword(password.text.get())) {
            if (!isValidEmail(email.text.get())) {
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

            if (!isValidName(confirmPassword.text.get())) {
                confirmPassword.setError(true)
            } else {
                confirmPassword.setError(false)
            }
        } else {
            email.setError(false)
            phone.setError(false)
            confirmPassword.setError(false)
            name.setError(false)
            password.setError(false)
        }
    }
}