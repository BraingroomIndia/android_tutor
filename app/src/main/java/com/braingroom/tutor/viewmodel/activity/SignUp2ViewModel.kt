package com.braingroom.tutor.viewmodel.activity

import android.util.Log
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by ashketchup on 4/12/17.
 */
class SignUp2ViewModel : ViewModel() {
    val genders by lazy {
        LinkedHashMap<String, Int>()
    }
    val communityClassData by lazy {
        LinkedHashMap<String, Int>()
    }
    val interestData by lazy {
        LinkedHashMap<String, Int>()
    }

    init {
        interestData.put("Fun & Recreation", 0)
        interestData.put("Health & Fitness", 1)
        communityClassData.put("Parents & Kids", 0)
        communityClassData.put("Study Groups", 1)
        genders.put("Male", 0)
        genders.put("Female", 1)

    }
}