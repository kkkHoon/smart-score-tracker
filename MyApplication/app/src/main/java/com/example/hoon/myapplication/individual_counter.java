package com.example.hoon.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hoon on 2017-11-15.
 */

public class individual_counter extends AppCompatActivity {

    TextView num_count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_counter);
        num_count = (TextView)findViewById(R.id.counter);
    }

    public void ShowHelper(View view)   //TODO
    {

    }

    public void counter(View view)
    {
        int id = view.getId();
        int value = Integer.parseInt(num_count.getText().toString());
        if(id == R.id.plus_btn)
        {
            if(value == 6)
                Toast.makeText(this,"한 테이블 당 최대 인원에 도달했습니다.",Toast.LENGTH_SHORT).show();
            else
            {
                String temp = String.valueOf(value + 1);
                num_count.setText(temp);
            }
        }
        else if(id == R.id.minus_btn)
        {
            if(value == 1)
                Toast.makeText(this,"한 테이블 당 최소 한 명의 플레이어가 필요합니다.", Toast.LENGTH_SHORT).show();
            else
            {
                String temp = String.valueOf(value - 1);
                num_count.setText(temp);
            }
        }
    }
}
