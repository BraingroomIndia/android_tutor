package com.braingroom.tutor.viewmodel.item

import android.graphics.Color
import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 7/12/17.
 */
class NotificationItemViewModel constructor() : ViewModel(){
    var color:Int =0
    lateinit var text:String
    val clicked: Action by lazy {
        object :Action{
            override fun run() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
    constructor(color:Boolean ,text:String ):this(){
        if(color==true){
                    this.color= R.color.materialBlueGray
                    this.color=R.color.material_white
        }
        this.text=text
    }
}