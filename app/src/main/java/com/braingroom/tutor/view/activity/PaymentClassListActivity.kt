package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.PaymentClassListViewModel
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


/*
 * Created by godara on 06/02/18.
 */
class PaymentClassListActivity : Activity() {
    override val vm by lazy {
        PaymentClassListViewModel(helperFactory)
    }
    override val layoutId: Int
        get() = R.layout.activity_payment_class_list


    var searchView: SearchView? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.action_bar_activity_payment_class_list, menu)
        searchView = menu.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(onSearch)
        //searchView?.setOnKeyListener(this::onSearchKeyClicked)

        return super.onCreateOptionsMenu(menu)
    }

    var onSearch: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            return true //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            vm.keyWord = query ?: ""
            vm.reset()
            (searchView?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(searchView?.windowToken, 0) // hide keyborad
            return true
        }
    }
}