package com.braingroom.tutor.view.activity


import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar

import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.viewmodel.activity.HomeViewModel

/*
 * Created by godara on 27/09/17.
 */
class HomeActivity : Activity(), NavigationView.OnNavigationItemSelectedListener {


    override val vm: HomeViewModel by lazy {
        HomeViewModel(helperFactory)
    }
    override val layoutId: Int by lazy {
        R.layout.activity_home
    }

    private var nonfictionItem: MenuItem? = null
    override val backButtonEnalebd = false

    var navigationView: NavigationView? = null
    var drawer: DrawerLayout? = null
    var toggle: ActionBarDrawerToggle? = null;
    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navigationView?.setNavigationItemSelectedListener(this)
        initNavigationDrawer()
    }

    fun initNavigationDrawer() {

        toggle = object : ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state.  */
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }
        drawer?.addDrawerListener(toggle!!)
        toggle?.syncState()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.nav_profile -> {
                navigator.navigateActivity(MyProfileActivity::class.java)
                true
            }
            item.itemId == R.id.nav_notification -> {
                navigator.navigateActivity(NotificationActivity::class.java)
                true
            }
            item.itemId == R.id.nav_message -> {
                navigator.navigateActivity(MessageActivity::class.java)
                true
            }
            item.itemId == R.id.nav_logout -> {
                messageHelper.showAcceptableInfo("Log out?", "Are you sure you want to log out of the app", "Confirm", SingleButtonCallback { dialog, which ->
                    vm.logout()
                    navigator.navigateActivity(Intent(this, SplashActivity::class.java).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

                })
                true
            }
            item.itemId == R.id.nav_faq -> {
                navigator.navigateActivity(FAQActivity::class.java)
                true
            }
            item.itemId == R.id.nav_change_password -> {
                navigator.navigateActivity(ChangePasswordActivity::class.java)
                return true
            }
            else -> true
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_activity_home, menu)
        nonfictionItem = menu.findItem(R.id.action_notifications)
        apiService.getUnreadNotificationCount().subscribe(this::setNotificationCount, vm::handleError)
        /*  itemMessage = menu.findItem(R.id.action_messages)
          setNotificationCount(itemNotification, this, ViewModel.notificationCount)
          setNotificationCount(itemMessage, this, ViewModel.messageCount)*/
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_notifications) {
            navigator.navigateActivity(NotificationActivity::class.java)
            return true
        }
        /*   if (item?.itemId == android.R.id.home) {
               return toggle?.onOptionsItemSelected(item) ?: false

           }*/
        return super.onOptionsItemSelected(item)
    }

    private fun setNotificationCount(count: Int) {
        if (nonfictionItem != null) {
            val badge: BadgeDrawable
            val icon = nonfictionItem?.icon as? LayerDrawable
            val reuse = icon?.findDrawableByLayerId(R.id.ic_badge)
            if (reuse != null && reuse is BadgeDrawable) {
                badge = reuse
            } else {
                badge = BadgeDrawable(this)
            }

            badge.setCount(count)
            icon?.mutate()
            icon?.setDrawableByLayerId(R.id.ic_badge, badge)
        }
    }
}


/* ;
                    startActivity(i);*/