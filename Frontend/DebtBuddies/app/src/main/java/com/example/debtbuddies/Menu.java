package com.example.debtbuddies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


public class Menu extends AppCompatActivity  {
    Button b_back, b_menu;
    ImageView icon;
    TextView tv_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tv_username = findViewById(R.id.tv_username);

        b_back = findViewById(R.id.b_back);
        b_menu = findViewById(R.id.b_main_menu);

        icon = findViewById(R.id.icon);

        Account t = new Account();
        if (t.icon.equals(" ")) {
            t.setIcon("icon3");
        }
        int  image = getResources().getIdentifier(t.icon, "drawable", getPackageName());
        icon.setImageResource(image);

    }

    public void onMenuClicked (View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
    public void onIconsClicked (View view) {
        Intent intent = new Intent(this, ProfileIcons.class);
        startActivity(intent);
    }
}
