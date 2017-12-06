package com.braingroom.tutor.view.activity

import android.app.FragmentTransaction
import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.view.fragment.DynamicSearchSelectFragment
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.view.fragment.SignUpFragment
import com.braingroom.tutor.viewmodel.activity.SignupViewModel

/**
 * Created by ashketchup on 30/11/17.
 */
class SignupActivity:Activity(){

    override val layoutId: Int = R.layout.activity_signup;

    override val vm: SignupViewModel by lazy {
       SignupViewModel(messageHelper,navigator,object:UiHelper{
           override fun firstFragment() {
               changeToFirstFragment();
           }

           override fun secondFragment() {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

           override fun thirdFragment() {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }
       },object:FragmentHelper{
           override fun show(tag: String) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

           override fun remove(tag: String) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }
       },object:FragmentHelper{
           override fun show(tag: String) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

           override fun remove(tag: String) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }
       })
    }
    fun changeToFirstFragment(){
        val fmt:FragmentTransaction = fragmentManager.beginTransaction()
        //  Different Implementation of TAGs
        fmt.add(R.id.fragment_container,SignUpFragment.newInstance(SignUpFragment.TAG)).addToBackStack(DynamicSearchSelectFragment.TAG).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    interface UiHelper{
        fun firstFragment()
        fun secondFragment()
        fun thirdFragment()
    }
}