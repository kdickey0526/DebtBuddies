package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateAccountActivity extends AppCompatActivity {

    // as a note it's almost certainly better to implement this as a fragment and not an activity but at this point
    // i do not really care so this is fine
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }
}