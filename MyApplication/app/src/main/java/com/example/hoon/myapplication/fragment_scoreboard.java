package com.example.hoon.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by hoon on 2017-11-26.
 */

public class fragment_scoreboard extends Fragment{

    private ListView listView;

    public fragment_scoreboard() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //temporary test inpt data
        users_info test = new users_info();
        test.addUser(new user_info());
        test.addUser(new user_info());
        test.addUser(new user_info());

        user_info test2 = new user_info();
        test2.setMaxScore(10);
        test2.setScore(5);
        test.addUser(test2);

        //make Adapter
        adapter_percentage ListAdapter = new adapter_percentage(test, getActivity());
        listView = (ListView)view.findViewById(R.id.listview);
        listView.setAdapter(ListAdapter);
    }
}
