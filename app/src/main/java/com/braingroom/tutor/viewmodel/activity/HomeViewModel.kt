package com.braingroom.tutor.viewmodel.activity


import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.braingroom.tutor.R
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.view.activity.*
import com.braingroom.tutor.view.activity.BarcodeCaptureActivity
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.view.fragment.AttendanceStatusFragment
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.HomeItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import com.braingroom.tutor.viewmodel.item.RecyclerViewItem
import io.reactivex.functions.Action
import java.util.*


/*
 * Created by godara on 27/09/17.
 */
class HomeViewModel(helperFactory: HelperFactory) : ViewModel(helperFactory) {

    val onProfilePicClick: Action by lazy {
        Action {
            navigator.navigateActivity(MyProfileActivity::class.java)
        }
    }
    val spanCount = 2


     fun getView(vm: RecyclerViewItem?): Int {

        return when (vm) {
            is HomeItemViewModel -> R.layout.item_home
            null -> throw NullPointerException()
            else -> throw NoSuchFieldError()
        }

    }

    private val bottomDrawableList = ArrayList<@ColorRes Int>(8)
    private val topDrawableList = ArrayList<@DrawableRes Int>(8)
    private val textList = ArrayList<String>(8)
    private val actionList = ArrayList<Action>(8)


    init {
        topDrawableList += R.drawable.ic_myclass_36dp//1
        textList += "My Class"   //1
        bottomDrawableList += R.color.material_blue400  //1
        actionList += Action { navigator.navigateActivity(MyClassesActivity::class.java) } //
        topDrawableList += R.drawable.ic_attendance_36dp//2
        textList += "Attendance" //2
        bottomDrawableList += R.color.material_lightgreen500 //2
        actionList += Action { navigator.navigateActivity(BarcodeCaptureActivity::class.java) } //2


        topDrawableList += R.drawable.ic_review_36dp//3
        textList += "Review" //3
        bottomDrawableList += R.color.material_pink500//3
        actionList += Action { navigator.navigateActivity(ReviewActivity::class.java) } //3


        topDrawableList += R.drawable.ic_promot_36dp//4
        textList += "Messaging" //4
        bottomDrawableList += R.color.material_lightgreen600//4
        actionList += Action { navigator.navigateActivity(BroadcastMessageActivity::class.java) } //4

        topDrawableList += R.drawable.ic_gallery_36dp//5
        textList += "Media" //5
        bottomDrawableList += R.color.material_purpleA100  //5
        actionList += Action { navigator.navigateActivity(MediaActivity::class.java) } //5


        topDrawableList += R.drawable.ic_payment_36dp//6
        textList += "Payment Details" //6
        bottomDrawableList += R.color.material_pink200  //6
        actionList += Action { navigator.navigateActivity(PaymentSummeryActivity::class.java) } //6



        topDrawableList += R.drawable.ic_calnder_36dp//7
        textList += "Booking Calender" //7
        bottomDrawableList += R.color.material_cyan300  //7
        actionList += Action { messageHelper.showDismissInfo("Info", "Comming Soon", "Dismiss") } //7




        topDrawableList += R.drawable.ic_add_class//8
        textList += "Add Class"  //8
        bottomDrawableList += R.color.material_pinkA400 //8
        actionList += Action { messageHelper.showDismissInfo("Info", "Comming Soon", "Dismiss") } //8




        (0..7).forEach { item.onNext(HomeItemViewModel(topDrawableList[it], bottomDrawableList[it], textList[it], actionList[it])) }
        item.onNext(NotifyDataSetChanged())
    }
}
