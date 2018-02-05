package com.braingroom.tutor.viewmodel.activity

import android.text.Spanned
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.data.ClassData
import com.braingroom.tutor.utils.fromHtml
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.ClassLocationViewModel
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import io.reactivex.functions.Action


/*
 * Created by godara on 25/01/18.
 */
class ClassDetailViewModel(helperFactory: HelperFactory, val data: ClassData) : ViewModel(helperFactory) {

    val classPic: String = data.classPic
    val isYoutubeVideo: Boolean = data.youtubeVideoId.isNullOrBlank()
    val isMapVisible = data.isMapVisible
    val classPrice: Spanned = fromHtml(data.priceSymbol + data.classPrice.toString())
    val classTopic: String = data.classTopic
    val sessionDurationInfo = data.noOfSession + " Session," + data.classDuration
    val classRating: String = data.classRating.toString()
    val teacherName: String = data.tutorName
    val teacherPic: String = data.teacherPic
    val classSummary: Spanned = fromHtml(data.classSummary)
    val playAction = Action { }
    val classLocationList: List<ClassLocationViewModel> = data.classLocationList.map {
        ClassLocationViewModel(it.locality, it.locationArea, {
            messageHelper.showDismissInfo("Address", it.locationArea, "Dismiss")
        })
    }
    val view: ViewProvider = object : ViewProvider {
        override fun getView(vm: RecyclerViewItem?): Int {
            return when (vm) {
                is ClassLocationViewModel -> R.layout.item_bullet_text
                null -> throw NullPointerException()
                else -> throw NoSuchFieldException()
            }
        }
    }


}
