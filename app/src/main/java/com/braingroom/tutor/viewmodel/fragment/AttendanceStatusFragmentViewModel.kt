package com.braingroom.tutor.viewmodel.fragment

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.req.AttendanceDetailReq
import com.braingroom.tutor.utils.getNonNull
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/**
 * Created by ashketchup on 28/12/17.
 */
class AttendanceStatusFragmentViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val isEndCode = ObservableBoolean(false)
    val startorendcode = ObservableField("")
    val onAttendance: Action by lazy {
        Action {
            messageHelper.showProgressDialog("Wait", "Communicating with server ");
            apiService.getStartOrEndDetails(AttendanceDetailReq(userId, startorendcode.get(), !isEndCode.get())).subscribe({ resp ->
                messageHelper.dismissActiveProgress()
                if (!resp.resCode)
                    messageHelper.showMessage(resp.resMsg)
                else
                    messageHelper.showAcceptableInfo("Ticket Info", resp.getData().getLearnerName() + resp.getData().getLearnerName(), "Confirm", "Cancel", SingleButtonCallback { dialog, which ->
                        apiService.updateAttendance(resp.data.learnerId, startorendcode.get(), !isEndCode.get()).subscribe({ resp ->
                            messageHelper.showMessage(resp.resMsg)
                        })
                    }, SingleButtonCallback { dialog, which ->
                    })
            })
        }
    }
}
