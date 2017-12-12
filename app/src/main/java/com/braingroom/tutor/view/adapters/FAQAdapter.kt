package com.braingroom.tutor.view.adapters

/**
 * Created by ashketchup on 12/12/17.
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bignerdranch.expandablerecyclerview.model.Parent
import com.braingroom.tutor.R

class FAQAdapter(context: Context, questionList: List<Question>) : ExpandableRecyclerAdapter<Question, String, FAQAdapter.QuestionViewHolder, FAQAdapter.AnswerViewHolder>(questionList) {

    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }

    // onCreate ...
    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): QuestionViewHolder {
        val questionView = mInflater.inflate(R.layout.question_view, parentViewGroup, false)
        return QuestionViewHolder(questionView)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): AnswerViewHolder {
        val answerView = mInflater.inflate(R.layout.answer_view, childViewGroup, false)
        return AnswerViewHolder(answerView)
    }

    // onBind ...
    override fun onBindParentViewHolder(recipeViewHolder: QuestionViewHolder, parentPosition: Int, question: Question) {
        recipeViewHolder.bind(question)
    }

    override fun onBindChildViewHolder(answerViewHolder: AnswerViewHolder, parentPosition: Int, childPosition: Int, answer: String) {
        answerViewHolder.bind(answer)
    }

    inner class QuestionViewHolder(itemView: View) : ParentViewHolder<Parent<View>,View>(itemView) {

        private val mQuestionTextView: TextView

        init {
            mQuestionTextView = itemView.findViewById<View>(R.id.question_textview) as TextView
        }

        fun bind(question: Question) {
            mQuestionTextView.setText(question.getText())
        }
    }

    inner class AnswerViewHolder(itemView: View) : ChildViewHolder<View>(itemView) {

        private val mAnswerTextView: TextView

        init {
            mAnswerTextView = itemView.findViewById<View>(R.id.answer_textview) as TextView
        }

        fun bind(answer: String) {
            mAnswerTextView.text = answer
        }
    }
}