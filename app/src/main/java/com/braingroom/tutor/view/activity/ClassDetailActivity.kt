package com.braingroom.tutor.view.activity

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import com.braingroom.tutor.R
import com.braingroom.tutor.model.data.ClassData
import com.braingroom.tutor.model.resp.ClassDetailResp
import com.braingroom.tutor.utils.classBudleData
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.ClassDetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch.BranchLinkShareListener
import io.branch.referral.BranchError
import io.branch.referral.SharingHelper
import io.branch.referral.util.ContentMetadata
import io.branch.referral.util.LinkProperties
import io.branch.referral.util.ShareSheetStyle
import java.util.*

/*
 * Created by godara on 28/01/18.
 */
class ClassDetailActivity : Activity() {
    lateinit var classData: ClassData
    lateinit var mShareActionProvider: ShareActionProvider

    val gson: Gson by lazy {
        GsonBuilder().create()
    }

    var mapFragment: SupportMapFragment? = null

    override val vm: ClassDetailViewModel by lazy {
        classData = getIntentSerializable(classBudleData) as ClassData
        ClassDetailViewModel(helperFactory, classData)
    }
    override val layoutId: Int
        get() = R.layout.activity_class_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this::populateMarkers)


    }

    private fun populateMarkers(map: GoogleMap?) {

        var markerOption: MarkerOptions
        classData.classLocationList.forEach { location ->
            try {
                markerOption = MarkerOptions().position(location.latLng).title(location.locationArea)
                map?.addMarker(markerOption)
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location.latLng, 10.0f))
            } catch (e: Exception) {
            }
        }

    }


    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu resource file.
        menuInflater.inflate(R.menu.action_baractivity_class_detail, menu);

        // Locate MenuItem with ShareActionProvider
        val shareItem: MenuItem = menu.findItem(R.id.menu_item_share)

        // Fetch and store ShareActionProvider


        // Return true to display menu
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_item_share) {
            shareBranchLink()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareBranchLink() {
        val buo = BranchUniversalObject().setCanonicalUrl(classData.classLink)
                .setCanonicalIdentifier(classData.classLink)
                .setTitle(classData.classTopic)
                .setContentDescription(classData.classSummary)
                .setContentImageUrl(classData.classPic)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)

        val lp = LinkProperties().setAlias(classData.classId)
                .setChannel(vm.userName)
                .setFeature("sharing")
                .setCampaign("QR Code").addTag(vm.userEmail)
                .addControlParameter("\$desktop_url", classData.classLink)
                .addControlParameter("qrcode", gson.toJson(ClassDetail(classData.classId)))


        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Checkout this class I found at Braingroom : " + buo.getShortUrl(this, lp))
        navigator.navigateActivity(Intent.createChooser(shareIntent, "Share link using"))
    }

    class ClassDetail(classId: String) {
        val type: String = "class_detail"

        @SerializedName("braingroom")
        val reqData: Snippet

        class Snippet(classId: String) {
            @SerializedName("id")
            val id: String = classId

            @SerializedName("promo_code")
            val promoCode: String = "" //TODO enter promocode

        }

        init {
            reqData = Snippet(classId)
        }
    }

}
