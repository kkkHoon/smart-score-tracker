package com.example.hoon.myapplication;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hoon on 2017-11-27.
 */

public class adapter_percentage extends BaseAdapter{

    private users_info data_list;
    private Context parent;
    private ArrayList<ImageButton> iconButton_list = new ArrayList<>();
    private ArrayList<ProgressBar> progressBar_list = new ArrayList<>();
    private ArrayList<TextView> textView_list = new ArrayList<>();

    private ImageButton.OnClickListener btn_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = iconButton_list.indexOf(view);
            if (parent != null)
            {
                Intent intent = new Intent(parent, user_info_view.class);
                intent.putExtra("position",position);
                parent.startActivity(intent);
            }
        }
    };

    public adapter_percentage() {
        this.data_list = new users_info();
        this.parent = null;
    }
    public adapter_percentage(Context parent) {
        this.data_list = new users_info();
        this.parent = parent;
    }

    public adapter_percentage(users_info list, Context parent){
        this.data_list = list;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return data_list.getNum();
    }

    @Override
    public Object getItem(int i) {
        try{
            return data_list.getUser(i);
        } catch (java.lang.Exception e) {};
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mode_percentage, viewGroup, false);
        }

        if (iconButton_list.size() <= i)  // although memory space waste, It is good for frequency updating situation(I think...)
        {
            ImageButton temp = (ImageButton)view.findViewById(R.id.user_info);
            temp.setOnClickListener(btn_listener);

            iconButton_list.add(i, temp);
            progressBar_list.add(i, (ProgressBar)view.findViewById(R.id.progressBar));
            textView_list.add(i, (TextView)view.findViewById(R.id.score_info));
        }

        user_info user_data = (user_info)getItem(i);
        if (user_data == null)
            return null;

        setScoreView(i, user_data);
        return view;
    }

    public void setScoreView(int position, user_info user_data) {
        int currentScore = user_data.getScore();
        int maxScore = user_data.getMaxScore();
        int progressBar_value;

        if (maxScore != 0)
            progressBar_value = (int)((currentScore / (float)maxScore) * 100);
        else
            progressBar_value = 0;

        progressBar_list.get(position).setProgress(progressBar_value);
        textView_list.get(position).setText(currentScore +" / "+ maxScore);
    }
}
