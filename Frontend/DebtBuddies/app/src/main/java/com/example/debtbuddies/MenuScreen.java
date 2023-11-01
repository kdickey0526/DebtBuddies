package com.example.debtbuddies;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MenuScreen extends Fragment {

    Button b_back, b_menu;
    TextView tv_username;

    public MenuScreen() {
        // Required empty public constructor
        b_back.findViewById(R.id.b_back);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        b_back.findViewById(R.id.b_back);
//        b_menu.findViewById(R.id.b_menu);
//        tv_username.findViewById(R.id.tv_username);
//        tv_username.setText("BoyInBlue");
        return inflater.inflate(R.layout.activity_menu, container, false);
    }



    public void onBackClicked (View view) {

    }
    public void onMenuClicked (View view) {

    }
}