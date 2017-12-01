package com.braingroom.tutor.utils

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.AnimatorRes

import android.util.Log
import com.braingroom.tutor.R

import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.view.fragment.BaseFragment
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.tbruyelle.rxpermissions2.RxPermissions

/*
 * Created by godara on 27/09/17.
 */

@Suppress("unused", "UNUSED_PARAMETER")
class Navigator(val activity: Activity?) {

    val TAG = activity?.TAG +"\t"+ this.javaClass.simpleName

    fun navigateActivity(destination: Class<out Activity>, bundle: Bundle) {
        val intent = Intent(activity, destination)
        intent.putExtra(bundleData, bundle)
        activity?.startActivity(intent)
    }

    fun navigateActivity(destination: Class<out Activity>) {
        val intent = Intent(activity, destination)
        activity?.startActivity(intent)
    }

    fun navigateActivityForResult(destination: Class<out Activity>, bundle: Bundle?, reqCode: Int) {
        val intent = Intent(activity, destination)
        intent.putExtra(bundleData, bundle)
        activity?.startActivityForResult(intent, reqCode)
    }


    fun navigateActivity(intent: Intent) {
        activity?.startActivity(intent)
    }

    fun finishActivity() {
        activity?.finish()
    }

    fun finishActivity(resultData: Intent, success: Boolean) {

        if (success) activity?.setResult(android.app.Activity.RESULT_OK, resultData)
        else activity?.setResult(android.app.Activity.RESULT_CANCELED, resultData)
        activity?.finish()
    }

    fun openFragment(fragment: BaseFragment) {
        activity?.fragmentManager?.
                beginTransaction()?.
                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                replace(R.id.fragment_container, fragment)?.
                addToBackStack(null)?.
                commit()
    }

    fun openFragment(fragment: BaseFragment, @AnimatorRes outAnimatorRes: Int, @AnimatorRes inAnimatorRes: Int) {
        activity?.fragmentManager?.
                beginTransaction()?.
                setCustomAnimations(outAnimatorRes, inAnimatorRes)?.
                replace(R.id.fragment_container, fragment)?.
                addToBackStack(null)?.
                commit()
    }

    fun openFragment(fragment: BaseFragment, fragmentContainer: Int) {
        activity?.fragmentManager?.
                beginTransaction()?.
                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                replace(fragmentContainer, fragment)?.
                addToBackStack(null)?.
                commit()
    }

    fun openFragment(fragment: BaseFragment, fragmentContainer: Int, @AnimatorRes outAnimatorRes: Int, @AnimatorRes inAnimatorRes: Int) {
        activity?.fragmentManager?.
                beginTransaction()?.
                setCustomAnimations(outAnimatorRes, inAnimatorRes)?.
                replace(fragmentContainer, fragment)?.
                addToBackStack(null)?.
                commit()
    }

    fun openStandaloneYoutube(videoId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun openStandaloneVideo(videoUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        intent.setDataAndType(Uri.parse(videoUrl), "video/*")
        activity?.startActivity(intent)
    }


    fun launchImageChooserActivity(reqCode: Int) {
        activity?.let(::RxPermissions)?.
                request(Manifest.permission.READ_EXTERNAL_STORAGE)?.subscribe { granted ->
            if (granted) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), reqCode)
            }
        }
    }

    fun launchVideoChooserActivity(reqCode: Int) {
        activity?.let(::RxPermissions)?.request(Manifest.permission.READ_EXTERNAL_STORAGE)?.subscribe { granted ->
            if (granted) {
                val intent = Intent()
                intent.type = "video/*"
                intent.action = Intent.ACTION_GET_CONTENT
                activity.startActivityForResult(Intent.createChooser(intent, "Select Video"), reqCode)
            }
        }
    }

    fun launchPlaceSearchIntent(reqCode: Int) {
        try {
            activity?.startActivityForResult(PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(activity), reqCode)
        } catch (e: GooglePlayServicesRepairableException) {
            Log.d("Place Api", "launchPlaceSearchIntent: " + e.message)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.d("Place Api", "launchPlaceSearchIntent: " + e.message)
        }
    }

}