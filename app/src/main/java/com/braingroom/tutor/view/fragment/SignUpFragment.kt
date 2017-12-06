package com.braingroom.tutor.view.fragment

import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 6/12/17.
 */
class SignUpFragment:BaseFragment(){
    companion object {
        val TAG="com.braingroom.tutor.view.fragment.SignUpFragment"
        fun newInstance(title:String):SignUpFragment {
            var bundle= Bundle()
            bundle.putString("title",title)
            var fragment=SignUpFragment()
            fragment.arguments=bundle
            return fragment
        }
    }
    override lateinit var vm: ViewModel

    override val layoutId: Int by lazy{
        R.layout.signup1
    }

}