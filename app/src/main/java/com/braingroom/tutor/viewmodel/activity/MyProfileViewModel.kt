package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import com.braingroom.tutor.R
import com.braingroom.tutor.model.resp.MyProfileResp.Achievement
import com.braingroom.tutor.model.resp.MyProfileResp.AssociatedTutors
import com.braingroom.tutor.model.resp.MyProfileResp.Snippet
import com.braingroom.tutor.utils.VERTICAL
import com.braingroom.tutor.utils.convertDpToPixel
import com.braingroom.tutor.view.adapters.*
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.ListTextIconViewModel
import com.braingroom.tutor.viewmodel.item.TextIconViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.ArrayList

/*
 * Created by godara on 11/10/17.
 */

class MyProfileViewModel() : ViewModel() {
    var items: ObservableField<List<ListTextIconViewModel>> = ObservableField();
    val viewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return R.layout.item_text_icon_list;
            }
        }
    }


    val decor: EqualSpacingItemDecoration  by lazy {
        EqualSpacingItemDecoration(convertDpToPixel(200).toInt(), VERTICAL)
    }

    init {
        apiService.getMyProfile("1131").observeOn(AndroidSchedulers.mainThread()).subscribe { resp -> if (resp.resCode) handleApiResult(resp.data) }
    }

    private fun handleApiResult(data: Snippet) {
        val source = ArrayList<ListTextIconViewModel>()
        source.add(getBasicDetails(data))
        source.add(getAcademicDetails(data))
        source.add(getAssociatedTeacher(data.associatedTutorsList))
        source.add(getAchievementTeacher(data.achievementList))
        items.set(source)
    }

    private fun getBasicDetails(data: Snippet): ListTextIconViewModel {
        val temp = ArrayList<TextIconViewModel>()
        temp.add(TextIconViewModel(data.name, R.drawable.ic_info_20dp))
        temp.add(TextIconViewModel(data.emailId, R.drawable.ic_ic_no__session_20dp))
        temp.add(TextIconViewModel(data.phoneNumber, R.drawable.ic_clock_20dp))
        temp.add(TextIconViewModel(data.address, R.drawable.ic_location_20dp))
        return ListTextIconViewModel("Basic Details", temp)
    }

    private fun getAcademicDetails(data: Snippet): ListTextIconViewModel {
        val temp = ArrayList<TextIconViewModel>()
        temp.add(TextIconViewModel(data.registrationId, R.drawable.ic_info_20dp))
        temp.add(TextIconViewModel(data.locality, R.drawable.ic_location_20dp))
        temp.add(TextIconViewModel(data.establishedSince, R.drawable.ic_clock_20dp))
        return ListTextIconViewModel("Academic Details", temp);
    }

    private fun getAssociatedTeacher(dataList: List<AssociatedTutors>): ListTextIconViewModel {
        val temp = ArrayList<TextIconViewModel>()
        while (dataList.listIterator().hasNext()) {
            temp.add(TextIconViewModel(dataList.listIterator().next().name, dataList.listIterator().next().icon))
        }
        return ListTextIconViewModel("Associate Teacher", temp)
    }

    private fun getAchievementTeacher(dataList: List<Achievement>): ListTextIconViewModel {
        val temp = ArrayList<TextIconViewModel>()
        while (dataList.listIterator().hasNext()) {
            temp.add(TextIconViewModel(dataList.listIterator().next().achievement, dataList.listIterator().next().achievementProof))
        }
        return ListTextIconViewModel("Associate Teacher", temp)
    }


}

