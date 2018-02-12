package com.braingroom.tutor.viewmodel.item

import android.databinding.generated.callback.OnClickListener
import android.text.Spanned
import android.view.View
import com.braingroom.tutor.model.resp.PaymentClassListResp
import io.reactivex.functions.Action
import java.util.function.Consumer

/*
 * Created by ashketchup on 19/12/17.
 */
class ItemPaymentDetailViewModel(val priceSymbol: Spanned, val title: String, val tickets: Int,
                                 val totalAmount: Int, val pendingAmount: Int,
                                 val receivedAmount: Int, val refundAmount: Int, val rating: Int,
                                 val image: String, val placeHolder: Int, val onClickListener: Action) : RecyclerViewItem {
    override val TAG: String
        get() = this::class.java.simpleName

    constructor(snippet: PaymentClassListResp.Snippet, onClickListener: Action) : this(snippet.priceSymbol, snippet.classTopic, snippet.totalTicket,
            snippet.totalAmount, snippet.pendingAmount, snippet.receviedAmount, snippet.refundAmount,
            snippet.classRating, snippet.classImage, snippet.placeHolder, onClickListener)

}