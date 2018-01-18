package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.model.resp.PaymentDetailsResp
import com.braingroom.tutor.viewmodel.ViewModel

/*
 * Created by ashketchup on 19/12/17.
 */
class ItemPaymentDetailViewModel(val title: String, val tickets: Int,
                                 val totalAmount: Int, val pendingAmount: Int,
                                 val receivedAmount: Int, val refundAmount: Int, val rating: Int,
                                 val image: String, val placeHolder: Int) : RecyclerViewItem {
    override val TAG: String
        get() = this::class.java.simpleName

    constructor(snippet: PaymentDetailsResp.Snippet) : this(snippet.classTopic, snippet.totalTicket,
            snippet.totalAmount, snippet.pendingAmount, snippet.receviedAmount, snippet.refundAmount,
            snippet.classRating, snippet.classImage, snippet.placeHolder)

}