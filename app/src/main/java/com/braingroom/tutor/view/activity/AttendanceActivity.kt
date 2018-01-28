package com.braingroom.tutor.view.activity

import android.util.Log
import android.util.SparseArray
import android.view.View
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.braingroom.tutor.R
import com.braingroom.tutor.model.req.AttendanceDetailReq
import com.braingroom.tutor.view.fragment.AttendanceStatusFragment
import com.braingroom.tutor.viewmodel.ViewModel
import com.google.android.gms.vision.barcode.Barcode
import info.androidhive.barcode.BarcodeReader


class AttendanceActivity : Activity(), BarcodeReader.BarcodeReaderListener {

    private val gson = applicationContext.appModule?.gson
    private var scanInProgress = false
    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScannedMultiple(barcodes: MutableList<Barcode>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCameraPermissionDenied() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScanned(barcode: Barcode?) {
        if (barcode == null || scanInProgress)
            return
        val req = gson!!.fromJson(barcode.displayValue, AttendanceDetailReq::class.java)
        Log.d("onBarcodeDetected", gson.toJson(AttendanceDetailReq(vm.userId, "123141234", true)));
        if (barcode.displayValue.contains("braingroom") == true && !scanInProgress) {
            scanInProgress = true
            messageHelper.showProgressDialog("Wait", "Communicating with server ");
            apiService.getStartOrEndDetails(req).subscribe({ resp ->
                messageHelper.dismissActiveProgress()
                if (!resp.resCode)
                    messageHelper.showDismissInfo("",resp.resMsg, "Cancel")
                else
                    messageHelper.showAcceptableInfo("Ticket Info", resp.getData().getLearnerName() + resp.getData().getLearnerName(), "Confirm", "Cancel", SingleButtonCallback { dialog, which ->
                        apiService.updateAttendance(resp.data.learnerId, req.data.startOrEndCode, req.data.isStartCode).subscribe({ resp ->
                            messageHelper.showDismissInfo("",resp.resMsg,"Dismiss")
                        })
                    }, SingleButtonCallback { dialog, which ->
                        scanInProgress = false
                    })
            })

        }
    }

    override fun onScanError(errorMessage: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val vm: ViewModel by lazy { ViewModel(helperFactory) }
    override val layoutId: Int
        get() = R.layout.activity_attendance

    fun enterStartEndCode(v: View) {
        navigator.navigateActivity(ManualAttendanceActivity::class.java)
    }
}

