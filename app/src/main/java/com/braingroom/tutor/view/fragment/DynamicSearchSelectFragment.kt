package com.braingroom.tutor.view.fragment

import android.content.Context
import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.SignupActivity
import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 6/12/17.
 */
class DynamicSearchSelectFragment:BaseFragment(){
    companion object {
        val TAG="com.braingroom.tutor.view.fragment.DynamicSearchSelectFragment"
        fun newInstance(title:String):DynamicSearchSelectFragment {
            var bundle=Bundle()
            bundle.putString("title",title)
            var fragment=DynamicSearchSelectFragment()
            fragment.arguments=bundle
            return fragment
        }
    }
    override lateinit var vm: ViewModel

    override val layoutId: Int by lazy {
        R.layout.fragment_dynamic_search_select_list
    }

    override fun onAttach(context: Context?) {
        activity.getFragmentViewmodel(DynamicSearchSelectFragment.TAG)
        super.onAttach(context)
    }
}