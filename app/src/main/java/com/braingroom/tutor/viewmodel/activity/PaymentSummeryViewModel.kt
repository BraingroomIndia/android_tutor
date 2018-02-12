package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import android.text.Spanned
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.PaymentSummaryResp
import com.braingroom.tutor.view.activity.PaymentClassListActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/*
 * Created by godara on 06/02/18.
 */
class PaymentSummeryViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val totalBookedTickets = ObservableField("")
    val amountReceived = ObservableField("")
    val amountPending = ObservableField("")
    val totalAmount = ObservableField("")
    val priceSymbol = ObservableField<Spanned>()
    val onGetDetailsClicked by lazy {
        Action {
            navigator.navigateActivity(PaymentClassListActivity::class.java)
        }
    }

    init {
        apiService.getPaymentSummary("", "").doOnSubscribe {
            compositeDisposable.add(it)
            messageHelper.showProgressDialog("Wait", "Loading")
        }.subscribe(this::handleApiResult, this::handleError)
    }

    private fun handleApiResult(resp: PaymentSummaryResp) {
        messageHelper.dismissActiveProgress()
        if (resp.resCode) {
            val data = resp.data
            priceSymbol.set(data.priceSymbol)
            totalBookedTickets.set(data.totalTicket)
            amountReceived.set(data.receivedAmount)
            amountPending.set(data.pendingAmount)
            totalAmount.set(data.totalAmount)
        }
    }
}