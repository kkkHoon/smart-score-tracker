package com.example.hoon.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by hoon on 2017-11-26.
 */

public class individual_scoreboard extends AppCompatActivity {

    Handler handler = new Handler();
    TextView timerText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_scoreboard);
        timerText = (TextView)findViewById(R.id.timer_text);

        timerThread timer = new timerThread();
        timer.setDaemon(true);
        timer.start();
    }

    public class timerThread extends Thread{

        private int hour, min;

        public timerThread() {
            this.hour = 0;
            this.min = 0;
        }

        @Override
        public void run() {
            while(true)
            {
                try {
                    Thread.sleep(1000 * 60);   // wait 60 seconds
                } catch (InterruptedException e) {}

                if (min == 59) {
                    hour += 1;
                    min = 0;
                }
                else
                    min += 1;

                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        //String value = ( (hour < 10)?"0"+hour:hour) +" : "+ ( (min < 10)?"0"+min:min);
                        String value = String.format("%02d",hour) + " : " + String.format("%02d",min);
                        timerText.setText(value);
                    }
                });

            }
        }
    }
}
