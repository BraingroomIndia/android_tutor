package com.braingroom.tutor.view.fragment

import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.fragment.AttendanceStatusFragmentViewModel

/**
 * Created by ashketchup on 28/12/17.
 */
class AttendanceStatusFragment : BaseFragment(){
    companion object {
        fun newInstance():AttendanceStatusFragment{
            return AttendanceStatusFragment()
        }
    }

    override val vm: ViewModel by lazy{
        AttendanceStatusFragmentViewModel()
    }
    override val layoutId: Int = R.layout.fragment_attendance_status
}