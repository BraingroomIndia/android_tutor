package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.MyProfileResp
import com.braingroom.tutor.model.resp.MyProfileResp.Snippet
import com.braingroom.tutor.utils.VERTICAL
import com.braingroom.tutor.utils.convertDpToPixel
import com.braingroom.tutor.view.adapters.*
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.*
import io.reactivex.android.schedulers.AndroidSchedulers

/*
 * Created by godara on 11/10/17.
 */

class MyProfileViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {
    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: RecyclerViewItem?): Int {
                return when (vm) {
                    is ListMyProfileItem -> R.layout.item_text_icon_list
                    null -> throw NullPointerException()
                    else -> throw NoSuchFieldError()
                }
            }
        }
    }


    val decor: EqualSpacingItemDecoration  by lazy {
        EqualSpacingItemDecoration(convertDpToPixel(200).toInt(), VERTICAL)
    }

    init {
        apiService.getMyProfile(userId).observeOn(AndroidSchedulers.mainThread()).doFinally { item.onNext(NotifyDataSetChanged()) }.subscribe { resp -> if (resp.resCode) handleApiResult(resp) }
    }

    private fun handleApiResult(resp: MyProfileResp) {
        item.onNext(getSection1(resp))
        item.onNext(getSection2(resp))
        item.onNext(getSection3(resp))

    }


    private fun getSection1(resp: MyProfileResp): ListMyProfileItem {
        val myProfileItemList = ArrayList<MyProfileItem>(4)
        return ListMyProfileItem("Section 1", resp.section1.mapTo(myProfileItemList) { MyProfileItem(it) })
    }

    private fun getSection2(resp: MyProfileResp): ListMyProfileItem {
        val myProfileItemList = ArrayList<MyProfileItem>(4)
        return ListMyProfileItem("Section 2", resp.section2.mapTo(myProfileItemList) { MyProfileItem(it) })
    }

    private fun getSection3(resp: MyProfileResp): ListMyProfileItem {
        val myProfileItemList = ArrayList<MyProfileItem>(4)
        return ListMyProfileItem("Section 3", resp.section3.mapTo(myProfileItemList) { MyProfileItem(it) })
    }


}

