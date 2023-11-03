package com.example.debtbuddies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ProfileIcons extends AppCompatActivity {
    String icon;
    ImageView playerIcon;
    CardView playerIcon0, playerIcon1, playerIcon2, playerIcon3, playerIcon4,
            playerIcon5, playerIcon6, playerIcon7, playerIcon8;

    Button b_icon0, b_icon1, b_icon2, b_icon3, b_icon4, b_icon5, b_icon6, b_icon7,
            b_icon8, b_frag;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icons);

        playerIcon = findViewById(R.id.icon);
        playerIcon0 = findViewById(R.id.icon0);
        playerIcon1 = findViewById(R.id.icon1);
        playerIcon2 = findViewById(R.id.icon2);
        playerIcon3 = findViewById(R.id.icon3);
        playerIcon4 = findViewById(R.id.icon4);
        playerIcon5 = findViewById(R.id.icon5);
        playerIcon6 = findViewById(R.id.icon6);
        playerIcon7 = findViewById(R.id.icon7);
        playerIcon8 = findViewById(R.id.icon8);


        b_icon0 = findViewById(R.id.b_icon0);
        b_icon1 = findViewById(R.id.b_icon1);
        b_icon2 = findViewById(R.id.b_icon2);
        b_icon3 = findViewById(R.id.b_icon3);
        b_icon4 = findViewById(R.id.b_icon4);
        b_icon5 = findViewById(R.id.b_icon5);
        b_icon6 = findViewById(R.id.b_icon6);
        b_icon7 = findViewById(R.id.b_icon7);
        b_icon8 = findViewById(R.id.b_icon8);
        b_frag = findViewById(R.id.b_menu);


//        getSupportFragmentManager().beginTransaction().add(R.id.frag_menu, new FirstFragment()),commit();
    }
    public void menu(View view) {

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void onButtonClick0(View view) {
        image = getResources().getIdentifier("icon0", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        Account t = new Account();
        t.setIcon("icon0");
    }
    public void onButtonClick1(View view) {
        image = getResources().getIdentifier("icon1", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        Account t = new Account();
        t.setIcon("icon1");
    }
    public void onButtonClick2(View view) {
        image = getResources().getIdentifier("icon2", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        Account t = new Account();
        t.setIcon("icon2");
    }
    public void onButtonClick3(View view) {
        image = getResources().getIdentifier("icon3", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        Account t = new Account();
        t.setIcon("icon3");
    }
    public void onButtonClick4(View view) {
        image = getResources().getIdentifier("icon4", "drawable", getPackageName());
        playerIcon.setImageResource(image);
    }
    public void onButtonClick5(View view) {
        image = getResources().getIdentifier("icon5", "drawable", getPackageName());
        playerIcon.setImageResource(image);
    }
    public void onButtonClick6(View view) {
        image = getResources().getIdentifier("icon6", "drawable", getPackageName());
        playerIcon.setImageResource(image);
    }
    public void onButtonClick7(View view) {
        image = getResources().getIdentifier("icon7", "drawable", getPackageName());
        playerIcon.setImageResource(image);
    }
    public void onButtonClick8(View view) {
        image = getResources().getIdentifier("icon8", "drawable", getPackageName());
        playerIcon.setImageResource(image);
    }

    public void onMenuClicked(View view) {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
    }

}
