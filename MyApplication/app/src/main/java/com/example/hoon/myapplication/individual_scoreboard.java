package com.example.hoon.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hoon on 2017-11-26.
 */

public class individual_scoreboard extends AppCompatActivity {

    Handler handler = new Handler();
    private static final int EXIT = 0;
    private static final int TIMESET = 1;
    Button test_buttons[] = new Button[3];

    private Handler timer_handler;
    TextView timerText;
    fragment_scoreboard fragment_in_view;
    users_info test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_scoreboard);
        timerText = (TextView)findViewById(R.id.timer_text);
        fragment_in_view = (fragment_scoreboard)getFragmentManager().findFragmentById(R.id.container);

        //temporary test inpt data
        test = new users_info();

        user_info temp1 = new user_info();
        temp1.setMaxScore(20);
        temp1.setName("KIM");
        user_info temp2 = new user_info();
        temp2.setMaxScore(5);
        temp2.setName("LEE");
        user_info temp3 = new user_info();
        temp3.setMaxScore(10);
        temp3.setName("AHN");
        user_info temp4 = new user_info();
        temp4.setMaxScore(15);
        temp4.setName("YOU");

        test.addUser(temp1);
        test.addUser(temp2);
        test.addUser(temp3);
        test.addUser(temp4);
        //temporary test input data end!

        fragment_in_view.setData(test);
        setTestButtons();   // TODO delete it after connecting real image processing video

        //timer thread setting
        timerThread timer = new timerThread();
        timer.setDaemon(true);
        timer.start();
        A_Minute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer_handler != null){
            Message msg = timer_handler.obtainMessage(EXIT);
            timer_handler.sendMessage(msg);
        }
    }

    public void setTestButtons(){
        test_buttons[0] = (Button)findViewById(R.id.plus_point);  // +1
        test_buttons[1] = (Button)findViewById(R.id.plus_zero);   // +0
        test_buttons[2] = (Button)findViewById(R.id.minus_point); // -1

        test_buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_in_view.plusEvent();
            }
        });
        test_buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_in_view.zeroEvent();
            }
        });
        test_buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_in_view.minusEvent();
            }
        });
    }

    private void A_Minute(){
        Log.d("bbb","A_Minute ENTERED!");
        if (timer_handler != null){
            Log.d("bbb","AFTER A_Minute ENTERED!");
            Message msg = timer_handler.obtainMessage(TIMESET);
            timer_handler.sendMessage(msg);
        }
    }

    public class timerThread extends Thread{

        private int hour, min;

        public timerThread() {
            this.hour = 0;
            this.min = 0;

            timer_handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case EXIT:
                            timer_handler.getLooper().quit();
                            break;
                        case TIMESET:
                            Log.d("bbb","TIMESET ENTERED!");
                            if (min == 59){
                                hour += 1;
                                min = 0;
                            }
                            else
                                min += 1;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String value = String.format("%02d",hour) + " : " + String.format("%02d",min);
                                    timerText.setText(value);
                                    A_Minute();
                                }
                            },60000);
                            break;
                    }
                }
            };
        }

        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        } // run() end
    }//timer Thread class end
}
