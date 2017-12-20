package com.braingroom.tutor.viewmodel.activity

import android.view.View
import com.braingroom.tutor.R
import com.braingroom.tutor.model.data.InputTypeEnum
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.SignupActivity
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.fragment.SearchSelectListViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel
import io.reactivex.functions.Action
import com.braingroom.tutor.viewmodel.item.TextIconViewModel
import io.reactivex.functions.Consumer

/*
 * Created by ashketchup on 30/11/17.
 */
class SignupViewModel(val uiHelper: SignupActivity.UiHelper, val fragmentHelper: FragmentHelper) : ViewModel() {
    val FIRST_FRAGMENT = "firstFragment"
    val SECOND_FRAGMENT = "secondFragment"

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


    val categoryVm by lazy {
        ListDialogViewModel("Interest", apiService.getCategories().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->

            val list: ListDialogData = ListDialogData(LinkedHashMap())
            for (snippet in resp.data)
                list.getItems().put(snippet.textValue, snippet.id)
            list

        }, HashMap(), true, Consumer { /*TODO*/ }, "", "Done")
    }
    val communityVm by lazy {
        ListDialogViewModel("Interest", apiService.getCategories().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->

            val list: ListDialogData = ListDialogData(LinkedHashMap())
            for (snippet in resp.data)
                list.getItems().put(snippet.textValue, snippet.id)
            list

        }, HashMap(), true, Consumer { /*TODO*/ }, "", "Done")
    }

    val countryVm by lazy {
        SearchSelectListViewModel(Country, "search country", "", false, apiService.getCountry().map { resp ->
            val list: HashMap<String, Int> = HashMap();
            for (snippet in resp.data)
                list.put(snippet.textValue, snippet.id)
            list
        }, Consumer { selectedData ->
            selectedData.values.forEach { id ->
                stateVm.refreshDataMap(apiService.getState(id).map { resp ->
                    val list: HashMap<String, Int> = HashMap();
                    resp.data.forEach { snippet -> list.put(snippet.textValue, snippet.id) }
                    list
                })
            }
        }, HashMap(), fragmentHelper)
    }
    val stateVm by lazy {
        SearchSelectListViewModel(State, "search state", "select country first", false, null, Consumer { selectedData ->
            selectedData.values.forEach { id ->
                cityVm.refreshDataMap(apiService.getCity(id).map { resp ->
                    val list: HashMap<String, Int> = HashMap();
                    resp.data.forEach { snippet -> list.put(snippet.textValue, snippet.id) }
                    list
                })
            }
        }, HashMap(), fragmentHelper)
    }
    val cityVm by lazy {
        SearchSelectListViewModel(City, "search city", "select state first", false, null, Consumer { selectedData ->
            selectedData.values.forEach { id ->
                localityVm.refreshDataMap(apiService.getLocality(id).map { resp ->
                    val list: HashMap<String, Int> = HashMap();
                    resp.data.forEach { snippet -> list.put(snippet.textValue, snippet.id) }
                    list
                })
            }
        }, HashMap(), fragmentHelper)
    }
    val localityVm by lazy {
        SearchSelectListViewModel(Locality, "search locality", "select city first", false, null, Consumer { /*TODO*/ }, HashMap(), fragmentHelper)
    }
    val onSignupClicked by lazy {
        Action {
            signup()
        }
    }

    init {
        uiHelper.firstFragment()
    }


    fun signup() {
        uiHelper.secondFragment()
        return
        /*   if (!(isValidEmail(email.text.get()) && isValidName(name.text.get()) && isValidPhone(phone.text.get()) && isValidName(confirmPassword.text.get())) && isValidPassword(password.text.get())) {
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
       }*/
    }
}