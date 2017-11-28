package com.example.hoon.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by hoon on 2017-11-26.
 */

public class user_info {

    private String user_name;
    private int user_score;
    private int user_max_score;
    //private Drawable icon;

    public user_info() {
        user_name = "noname";
        user_score = 0;
        user_max_score = 0;
    }

    public String getName() {
        return user_name;
    }
    public int getScore() {
        return user_score;
    }
    public int getMaxScore() {
        return user_max_score;
    }

    public void setName(String name) {
        user_name = name;
    }
    public void setScore(int score) {
        user_score = score;
    }
    public void setMaxScore(int score) {
        user_max_score = score;
    }
}
