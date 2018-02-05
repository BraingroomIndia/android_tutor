package com.braingroom.tutor.view.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.braingroom.tutor.R
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.viewmodel.ViewModel
import java.io.Serializable

/*
 * Copyright 2016 Manas Chaudhari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



/**
 * Inflates the provided view and binds the provided ViewModel based on default
 * binder provided to the library
 */
abstract class Activity : AppCompatActivity() {
    protected lateinit var binding: ViewDataBinding


    private val extras: Bundle? by lazy {
        intent?.extras?.getBundle(bundleData)
    }

    val TAG: String
        get() = this::class.java.simpleName ?: ""


    @Suppress("unused")
    val applicationContext: CustomApplication by lazy {
        CustomApplication.getInstance()
    }

    @Suppress("unused")
    var loggedIn: Boolean
        get() = applicationContext.loggedIn
        set(value) {
            preferencesEditor.putBoolean(lodgedIn, value).commit()
            applicationContext.loggedIn = value
        }


    @Suppress("unused")
    val apiService by lazy {
        applicationContext.appModule.dataFlowService
    }

    @Suppress("unused")
    val messageHelper by lazy {
        helperFactory.messageHelper
    }

    @Suppress("unused")
    val navigator by lazy {
        helperFactory.navigator
    }

    @Suppress("unused")
    val dialogHelper by lazy {
        helperFactory.dialogHelper
    }

    private val userPreferences by lazy {
        applicationContext.appModule.userPreferences
    }

    private val preferencesEditor by lazy {
        applicationContext.appModule.preferencesEditor
    }

    val helperFactory by lazy {
        HelperFactory(this)
    }

    open val backButtonEnalebd = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        CustomApplication.getInstance().refWatcher?.watch(this)
        binding = DataBindingUtil.setContentView(this, layoutId)
        defaultBinder.bind(binding, vm)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onPause() {
        super.onPause()
        vm.onPause()
    }

    override fun onResume() {

        super.onResume()
        vm.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        vm.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        vm.onDestroy()
        defaultBinder.bind(binding, null)
        binding.executePendingBindings()

        super.onDestroy()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home && backButtonEnalebd) {
            finish()
            true
        } else super.onOptionsItemSelected(item)


    }

    open fun getFragmentViewModel(title: String) = ViewModel(helperFactory)

    @Suppress("unused")
    fun getIntentString(key: String) = extras?.getString(key) ?: ""

    @Suppress("unused")
    fun getIntentInt(key: String) = extras?.getInt(key)

    @Suppress("unused")
    fun getIntentBoolean(key: String) = extras?.getBoolean(key) ?: false


    @Suppress("unused")
    fun getIntentSerializable(key: String): Serializable? = extras?.getSerializable(key)

    fun popBackStack(title: String) {
        val count = fragmentManager.backStackEntryCount
        if (count > 0) {
            fragmentManager.popBackStack(title, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

    }

    abstract val vm: ViewModel

    protected abstract val layoutId: Int
}

