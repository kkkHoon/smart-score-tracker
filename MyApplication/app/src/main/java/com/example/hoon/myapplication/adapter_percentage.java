package com.example.hoon.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hoon on 2017-11-27.
 */

public class adapter_percentage extends BaseAdapter{

    private users_info data_list;
    private ArrayList<View> item_list;
    private Context parent;

    Drawable waiting;
    Drawable playing;


    private ImageButton.OnClickListener btn_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (parent != null)
            {
                Intent intent = new Intent(parent, user_info_view.class);
                int position = item_list.indexOf(view);
                Log.d("ccc","position = " +position); // position return -1... maybe parameter view is not stored view. just inflated before
                try {
                    intent.putExtra("user_info", data_list.getUser(position));
                    parent.startActivity(intent);
                }catch (Exception e){e.printStackTrace();}
            }
        }
    };
    public adapter_percentage() {
        this.data_list = new users_info();
        this.item_list = new ArrayList<>();
        this.parent = null;
    }
    public adapter_percentage(Context parent) {
        this.data_list = new users_info();
        this.item_list = new ArrayList<>();
        this.parent = parent;
        this.waiting = parent.getResources().getDrawable(R.drawable.waiting);
        this.playing = parent.getResources().getDrawable(R.drawable.playing);
    }

    public adapter_percentage(users_info list, Context parent){
        this.data_list = list;
        this.item_list = new ArrayList<>();
        this.parent = parent;
        this.waiting = parent.getResources().getDrawable(R.drawable.waiting);
        this.playing = parent.getResources().getDrawable(R.drawable.playing);
    }

    @Override
    public int getCount() {
        return data_list.getLength();
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
        Log.d("aaa","getView entered :: position = " +i);

        if(item_list.size() > i) {  // 그 이후에 다시 getView()가 call될 때는 미리 저장해놓은 view를 재활용.
            try {
                update_view(item_list.get(i), i);
            }catch (Exception e) {e.printStackTrace();}
            return item_list.get(i);
        }
        else {  // 맨 처음에 각 item view를 inflate할 때 한번 씩 불려지고
            final Context context = viewGroup.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mode_percentage, viewGroup, false);

            try {
                Log.d("bbb","Max score(" +i +") = " +data_list.getUser(i).getMaxScore());
                data_list.getUser(i).setMyView(view);
                if(data_list.getUser(i).getTurn())  //처음 item view를 inflate할 때 지금 순서인 player는 view loading과 동시에 개인 타이머 작동
                    data_list.getUser(i).startTimer();
                ImageButton imageButton = (ImageButton)view.findViewById(R.id.user_info);
                imageButton.setOnClickListener(btn_listener);
                update_view(view, i);
            }catch (Exception e) {e.printStackTrace();}
            item_list.add(i,view);
            return view;
        }
    }

    private void update_view(View view, int position) throws Exception{
        user_info user_data = (user_info) getItem(position);
        if (user_data == null)
            throw new Exception();

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView textView = (TextView) view.findViewById(R.id.score_info);
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.user_info);


        if(user_data.getTurn()){ // playing
            if(imageButton.getDrawable() == null || !imageButton.getDrawable().equals(playing)) {
                if(Build.VERSION.SDK_INT >= 16)
                    imageButton.setBackground(playing);
                else
                    imageButton.setBackgroundDrawable(playing);
            }
        }
        else{
            if(imageButton.getDrawable() == null || !imageButton.getDrawable().equals(waiting)) {
                if(Build.VERSION.SDK_INT >= 16)
                    imageButton.setBackground(waiting);
                else
                    imageButton.setBackgroundDrawable(waiting);
            }
        }

        int currentScore = user_data.getScore();
        int maxScore = user_data.getMaxScore();
        int progressBar_value;

        if (maxScore != 0)
            progressBar_value = (int) ((currentScore / (float) maxScore) * 100);
        else
            progressBar_value = 0;

        progressBar.setProgress(progressBar_value);
        textView.setText(currentScore + " / " + maxScore);
    }
}
