package com.braingroom.tutor.view.adapters;

/**
 * Created by ashketchup on 12/12/17.
 */
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;



public class Question implements Parent<String> {

    private String text;
    private List<String> mAnswers = new ArrayList<>();


    public void setText(String text){
        this.text=text;
    }
    public String  getText(){
        return text;
    }

    public Question(String name, String answer) {
        this.text = name;
        mAnswers.add(answer);
    }

    @Override
    public List<String> getChildList() {
        return mAnswers;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
