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
class SignUp2ViewModel:ViewModel(){
    var communityClassPreference:ListDialogViewModel
    var interestAreas:ListDialogViewModel
    var gender:ListDialogViewModel
    val genders by lazy {
        LinkedHashMap<String,Int>()
    }
    val communityClassData by lazy{
        LinkedHashMap<String,Int>()
    }
    val interestData by lazy{
        LinkedHashMap<String,Int>()
    }
    init{
        interestData.put("Fun & Recreation",0)
        interestData.put("Health & Fitness",1)
        communityClassData.put("Parents & Kids",0)
        communityClassData.put("Study Groups",1)
        genders.put("Male",0)
        genders.put("Female",1)
        gender = ListDialogViewModel("Select Gender", Observable.just(ListDialogData(genders)), HashMap<String,Int>(),false,{ selectedItems ->
            if (selectedItems.values.iterator().hasNext()) {
                Log.d(TAG, "classType selected items : "+selectedItems.keys.iterator().next())
            }
        },"")
        interestAreas= ListDialogViewModel("Search Interest Areas", Observable.just(ListDialogData(interestData)),HashMap<String,Int>(),false,{selecteditems:java.util.HashMap<String,Int> ->
            if(selecteditems.values.iterator().hasNext()){
                Log.d(TAG, "classType selected items : "+selecteditems.keys.iterator().next())
        }
        },"")
        communityClassPreference= ListDialogViewModel("Community Class Preferences", Observable.just(ListDialogData(communityClassData)),HashMap<String,Int>(),false, {selectedItems:java.util.HashMap<String,Int> ->
            if (selectedItems.values.iterator().hasNext()) {
                Log.d(TAG, "classType selected items : "+selectedItems.keys.iterator().next())
            }
        },"")
    }

}