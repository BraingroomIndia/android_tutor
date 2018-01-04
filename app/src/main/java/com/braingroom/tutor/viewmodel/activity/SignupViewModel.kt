package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.model.req.SignUpReq
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.HomeActivity
import com.braingroom.tutor.view.activity.SignupActivity
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.fragment.SearchSelectListViewModel
import com.braingroom.tutor.viewmodel.item.DatePickerViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer


/*
 * Created by ashketchup on 30/11/17.
 */
class SignupViewModel(val uiHelper: SignupActivity.UiHelper, val fragmentHelper: FragmentHelper) : ViewModel() {

    val snippet: SignUpReq.Snippet = SignUpReq.Snippet()
    val isIndividual = ObservableBoolean(true)
    val FIRST_FRAGMENT = "firstfragment"
    val SECOND_FRAGMENT = "secondFragment"
    val THIRD_FRAGMENT = "thirdfragment"
    val name by lazy {
        ObservableField<String>("")
    }
    val phone by lazy {
        ObservableField<String>("")
    }
    val email by lazy {
        ObservableField<String>("")
    }
    val password by lazy {
        ObservableField<String>("")
    }
    val confirmPassword by lazy {
        ObservableField<String>("")
    }
    val referralCode by lazy {
        ObservableField<String>("")
    }
    val signUpButton by lazy {
        ObservableField<String>("")
    }

    val instituteName by lazy {
        ObservableField<String>("")
    }
    val instituteId by lazy {
        ObservableField<String>("")
    }
    val address by lazy {
        ObservableField<String>("")
    }
    val aboutYou by lazy {
        ObservableField<String>("")
    }

    val expertiseArea by lazy {
        ObservableField<String>("")
    }

    val uploadImage by lazy {
        Action {

        }
    }

    val datePicker by lazy {
        DatePickerViewModel(dialogHelper, "DOB", "12-12-2012")
    }


    val categoryVm by lazy {
        SearchSelectListViewModel(Category, "select Interest", "", false, apiService.getCategories().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->
            val list = HashMap<String, Int>()
            resp.data.forEach { snippet -> list.put(snippet.textValue, snippet.id) }
            list
        }, Consumer { selectedData ->
            snippet.categoryId = selectedData.getId()

        }, HashMap(), fragmentHelper)
    }

    val genderVm by lazy {
        ListDialogViewModel("Gender", apiService.getGender().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->

            val list: ListDialogData = ListDialogData(LinkedHashMap())
            for (snippet in resp.data)
                list.getItems().put(snippet.textValue, snippet.id)
            list

        }, HashMap(), true, Consumer { selectedData ->
            snippet.setGender(selectedData.getId())

        }, "", "Done")
    }

    val communityVm by lazy {
        SearchSelectListViewModel(Community, "Select Community", "", false, apiService.getCommunity().doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.map { resp ->
            val list = HashMap<String, Int>()
            resp.data.forEach { snippet -> list.put(snippet.textValue, snippet.id) }
            list
        }, Consumer { selectedData ->
            snippet.communityId = selectedData.getId()

        }, HashMap(), fragmentHelper)
    }

    val countryVm by lazy {
        SearchSelectListViewModel(Country, "search country", "", false, apiService.getCountry().map { resp ->
            val list = HashMap<String, Int>()
            resp.data.forEach { snippet -> list.put(snippet.textValue, snippet.id) }
            list
        }, Consumer { selectedData ->
            snippet.countryId = selectedData.getId()
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
            snippet.stateId = selectedData.getId()
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
            snippet.cityId = selectedData.getId()
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
        SearchSelectListViewModel(Locality, "search locality", "select city first", false, null, Consumer { selectedDataMap -> snippet.locality = selectedDataMap.getId() }, HashMap(), fragmentHelper)
    }
    val toFirst by lazy {
        Action {
            uiHelper.firstFragment()
        }
    }

    val toSecond by lazy {
        Action {
            if (validateFirstFragment())
                uiHelper.secondFragment()
        }
    }
    val toThird by lazy {
        Action {
            uiHelper.thirdFragment()
            snippet.areaOfExpertise = expertiseArea.get()
            snippet.address = address.get()
            snippet.description = aboutYou.get()
        }
    }
    val apiSignUp by lazy {
        Action {
            signUp()
        }
    }

    private fun validateFirstFragment(): Boolean {
        when {
            name.get().isNullOrBlank() -> {
                messageHelper?.showMessage("Name Can't be blank")
                return false
            }
            !email.get().isValidEmail() -> {
                messageHelper?.showMessage("Email id is not valid")
                return false
            }
            password.get().isNullOrBlank() || confirmPassword.get().isNullOrBlank() -> {
                messageHelper?.showMessage("Password can't be blank")
                return false
            }
            confirmPassword.get() != password.get() -> {
                messageHelper?.showMessage("Password don't match")
                return false
            }
            !phone.get().isValidPhone() -> {
                messageHelper?.showMessage("Mobile Number is not valid")
                return false
            }
            else -> {
                snippet.name = name.get()
                snippet.email = email.get()
                snippet.password = password.get()
                snippet.mobileNo = phone.get()
                return true
            }
        }
    }


    init {
        Log.d(TAG, "hello")
        uiHelper.firstFragment()
        /*   isIndividual.addOnPropertyChangedCallback(object : android.databinding.Observable.OnPropertyChangedCallback() {
               override fun onPropertyChanged(sender: android.databinding.Observable?, propertyId: Int) {
                   if (isIndividual.get())
                       aboutYou.hinttext.set("About You")
                   else
                       aboutYou.hinttext.set("About Institute")
               }
           })
           isIndividual.set(!isIndividual.get())
           uiHelper.firstFragment()*/

    }

    fun signUp() {
        if (validateFirstFragment()) {
            apiService.signUp(SignUpReq(snippet)).doOnSubscribe { disposable -> compositeDisposable.add(disposable) }.subscribe(
                    { resp ->
                        if (resp.resCode) {
                            val data = resp.data
                            if (signUpSuccess(name.get(), email.get(), "", data.userId)) {
                                navigator?.navigateActivity(HomeActivity::class.java)
                            }
                        } else {
                            messageHelper?.showMessage(resp.resMsg)
                            uiHelper.firstFragment()
                        }
                    })
        }
    }

    fun toThird() {
        /* if (datePicker.title.get().equals("DOB")) {
             snippet.dob = ""
         } else {
             snippet.dob = datePicker.title.get()
         }
         snippet.instituteName = instituteName.text.get()
         snippet.description = aboutYou.text.get()
         snippet.areaOfExpertise = expertiseArea.text.get()
         uiHelper.thirdFragment()*/
        return
    }


    fun toSecond() {
        /*  snippet.name = name.text.get()
          snippet.mobileNo = phone.text.get()
          snippet.email = phone.text.get()
          snippet.password = phone.text.get()
          snippet.referralCode = referralCode.text.get()
          uiHelper.secondFragment()*/
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

    private fun signUpSuccess(user: String, emailId: String,
                              profilePicture: String, userId: String): Boolean {
        try {
            loggedIn = true
            preferencesEditor.putBoolean(lodgedIn, true)
            preferencesEditor.putString("userName", user)
            preferencesEditor.putString("userEmail", emailId)
            preferencesEditor.putString(profilePic, profilePicture)
            preferencesEditor.putString(braingroomId, userId)
            preferencesEditor.apply()
            CustomApplication.getInstance().userEmail = emailId
            CustomApplication.getInstance().userId = userId
            CustomApplication.getInstance().userName = user
            CustomApplication.getInstance().userPic = profilePicture
        } catch (e: Exception) {
            return false
        }

        return true
    }
}