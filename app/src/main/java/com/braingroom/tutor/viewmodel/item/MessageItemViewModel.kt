package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 7/12/17.
 */
class MessageItemViewModel constructor(val text:String,val senderImage:String,val senderName:String,val sentDate:String): ViewModel(){
    val onClicked: Action by lazy{
        object:Action{
            override fun run() {


            }
        }
    }

}