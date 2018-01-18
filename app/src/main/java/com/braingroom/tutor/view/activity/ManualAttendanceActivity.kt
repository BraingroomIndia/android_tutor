package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.fragment.AttendanceStatusFragmentViewModel

/**
 * Created by godara on 08/01/18.
 */

class ManualAttendanceActivity : Activity() {
    override val vm: ViewModel by lazy {
        AttendanceStatusFragmentViewModel(helperFactory)
    }

    override val layoutId: Int
        get() = R.layout.fragment_attendance_status
}
