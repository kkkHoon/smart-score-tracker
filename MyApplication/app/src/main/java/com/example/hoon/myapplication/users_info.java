package com.example.hoon.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoon on 2017-11-26.
 */

public class users_info implements Parcelable {

    private ArrayList<user_info> user_list = new ArrayList<>();

    public int getNum() {
        return user_list.size();
    }

    public void addUser(user_info user) {
        user_list.add(user);
    }

    public user_info getUser(int position) throws Exception{
        if (position >= 0 && position < user_list.size())
        {
            return user_list.get(position);
        }
        throw new Exception();
    }

    public users_info() {}

    protected users_info(Parcel in) {
        in.readList(user_list,List.class.getClassLoader());
    }

    public static final Creator<users_info> CREATOR = new Creator<users_info>() {
        @Override
        public users_info createFromParcel(Parcel in) {
            return new users_info(in);
        }

        @Override
        public users_info[] newArray(int size) {
            return new users_info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(user_list);
    }
}
