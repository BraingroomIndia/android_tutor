package com.braingroom.tutor.viewmodel.fragment

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.braingroom.tutor.utils.getNonNull
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 28/12/17.
 */
class AttendanceStatusFragmentViewModel:ViewModel(){
    val isEndCode  = ObservableBoolean(false)
    val startorendcode  = ObservableField("")
    val onAttendance:Action by lazy{
        Action{
            apiService.getStartOrEndDetails(startorendcode.get(),isEndCode.get()).subscribe{ resp ->
                if(getNonNull(resp.data.ticketId).length>0)
                    dialogHelper?.showAttendance(resp.data.learnerId,startorendcode.get(),resp.data.learnerName,resp.data.classTopic,this)
                else
                    dialogHelper?.emptyAttendance(resp.resMsg)
            }
        }
    }
}