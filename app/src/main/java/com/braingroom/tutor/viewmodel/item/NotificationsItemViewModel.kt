package com.braingroom.tutor.viewmodel.item

import android.graphics.Color
import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.NotificationListResp
import com.braingroom.tutor.view.activity.ClassDetailActivity
import com.braingroom.tutor.viewmodel.ViewModel
import io.reactivex.functions.Action

/*
 * Created by ashketchup on 26/12/17.
 */
class NotificationsItemViewModel(helperFactory: HelperFactory, val title: String, val postId: String,
                                 val classId: String,
                                 val readStatus: Boolean) : ViewModel(helperFactory), RecyclerViewItem {
    val onClick: Action by lazy {
        Action {
            if (classId.isBlank())
                return@Action

            apiService.getClassDetail(classId).doOnError(this::handleError).doOnSubscribe { compositeDisposable.add(it) }.
                    subscribe({
                        if (!it.resCode)
                            return@subscribe
                        val bundle = Bundle()
                        bundle.putSerializable("classData", it.data)
                        navigator.navigateActivity(ClassDetailActivity::class.java, bundle)

                    })
        }
    }
    val color by lazy {
        if (readStatus)
            R.color.material_grey200
        else
            R.color.material_white
    }

    constructor(helperFactory: HelperFactory, snippet: NotificationListResp.Snippet) : this(helperFactory, snippet.getDescription(), "", snippet.getClassId(), snippet.getStatus())

}