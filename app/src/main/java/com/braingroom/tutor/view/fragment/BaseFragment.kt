package com.braingroom.tutor.view.fragment

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.braingroom.tutor.view.activity.Activity
import com.braingroom.tutor.viewmodel.ViewModel

import com.braingroom.tutor.utils.defaultBinder

/*
 * Created by godara on 05/12/17.
 */

abstract class BaseFragment : Fragment() {

    lateinit var binding: ViewDataBinding

    lateinit var activity: Activity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            activity = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        defaultBinder.bind(binding, vm)
        return binding.root
    }

    override fun onDestroy() {
        vm.onDestroy()
        defaultBinder.bind(binding, null)
        binding.executePendingBindings()
        super.onDestroy()
    }


    override fun onResume() {
        super.onResume()
        vm.onResume()
    }

    override fun onPause() {
        super.onPause()
        vm.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vm.onActivityResult(requestCode, resultCode, data)
    }

    @Suppress("unused")
    fun getStringArguments(key: String) = arguments?.getString(key) ?: ""

    @Suppress("unused")
    fun getIntArguments(key: String) = arguments?.getInt(key)

    @Suppress("unused")
    fun getSerializableArguments(key: String) = arguments?.getSerializable(key)


    protected abstract val vm: ViewModel

    protected abstract val layoutId: Int


}
