package com.braingroom.tutor.viewmodel.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.resp.MyProfileResp
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
        apiService.getMyProfile(userId).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe { compositeDisposable.add(it) }.map(this::mapRespToViewModel).subscribe(this::handleApiResp, this::handleError)
    }

    private fun mapRespToViewModel(resp: MyProfileResp): List<RecyclerViewItem>
            = listOf(getSection1(resp), getSection2(resp), getSection3(resp))

    private fun handleApiResp(viewModelList: List<RecyclerViewItem>) {
        viewModelList.forEach { item.onNext(it) }
        item.onNext(NotifyDataSetChanged())
    }


    private fun getSection1(resp: MyProfileResp): ListMyProfileItem {
        return ListMyProfileItem("Section 1", resp.section1.map { MyProfileItem(it) })
    }

    private fun getSection2(resp: MyProfileResp): ListMyProfileItem {
        return ListMyProfileItem("Section 2", resp.section2.map { MyProfileItem(it) })
    }

    private fun getSection3(resp: MyProfileResp): ListMyProfileItem {
        return ListMyProfileItem("Section 3", resp.section3.map { MyProfileItem(it) })
    }


}

