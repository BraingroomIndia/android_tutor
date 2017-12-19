package com.braingroom.tutor.viewmodel.activity

import android.content.Context
import android.support.annotation.UiThread
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.braingroom.tutor.R
import com.braingroom.tutor.view.adapters.FAQAdapter
import com.braingroom.tutor.view.adapters.Question
import com.braingroom.tutor.viewmodel.ViewModel
import java.util.*

/**
 * Created by ashketchup on 12/12/17.
 */
class FAQViewModel(val context: Context):ViewModel(){
    val q1 = Question(applicationContext.resources.getString(R.string.q1), applicationContext.resources.getString(R.string.a1))
    val q2 = Question(applicationContext.resources.getString(R.string.q2), applicationContext.resources.getString(R.string.a2))
    val q3 = Question(applicationContext.resources.getString(R.string.q3), applicationContext.resources.getString(R.string.a3))
    val q4 = Question(applicationContext.resources.getString(R.string.q4), applicationContext.resources.getString(R.string.a4))
    val q5 = Question(applicationContext.resources.getString(R.string.q5), applicationContext.resources.getString(R.string.a5))
    val q6 = Question(applicationContext.resources.getString(R.string.q6), applicationContext.resources.getString(R.string.a6))
    val q7 = Question(applicationContext.resources.getString(R.string.q7), applicationContext.resources.getString(R.string.a7))
    val q8 = Question(applicationContext.resources.getString(R.string.q8), applicationContext.resources.getString(R.string.a8))
    val q9 = Question(applicationContext.resources.getString(R.string.q9), applicationContext.resources.getString(R.string.a9))
    val q10 = Question(applicationContext.resources.getString(R.string.q10), applicationContext.resources.getString(R.string.a10))
    val q11 = Question(applicationContext.resources.getString(R.string.q11), applicationContext.resources.getString(R.string.a11))
    val q12 = Question(applicationContext.resources.getString(R.string.q12), applicationContext.resources.getString(R.string.a12))
    val questions = Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12)
    val layoutManager = LinearLayoutManager(context)
    val adapter:FAQAdapter = FAQAdapter(context,questions)

    init {
        questions
        adapter.setExpandCollapseListener(object : ExpandableRecyclerAdapter.ExpandCollapseListener {
            @UiThread
            override fun onParentExpanded(parentPosition: Int) {
                //                Question expandedRecipe = questions.get(parentPosition);
            }

            @UiThread
            override fun onParentCollapsed(parentPosition: Int) {
                //                Recipe collapsedRecipe = recipes.get(parentPosition);
            }
        })
    }
}