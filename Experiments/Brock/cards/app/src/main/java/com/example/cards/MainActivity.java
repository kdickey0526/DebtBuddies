package com.example.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView iv_cards_left, iv_cards_right;
    TextView tv_score_left, tv_score_right;
    Button b_deal;
    Random r;
    int leftScore = 0, rightScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iv_cards_left = findViewById(R.id.id_card_left);
        tv_score_left = findViewById(R.id.tv_score_left);

        iv_cards_right = findViewById(R.id.id_card_right);
        tv_score_right = findViewById(R.id.tv_score_right);

        b_deal = findViewById(R.id.b_deal);

        r = new Random();

        b_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int leftCard = r.nextInt(13) + 2; //values 2 - 14
                int rightCard = r.nextInt(13) + 2;

                int leftImage = getResources().getIdentifier("card" + leftCard, "drawable", getPackageName());
                iv_cards_left.setImageResource(leftImage);

                int rightImage = getResources().getIdentifier("card" + rightCard, "drawable", getPackageName());
                iv_cards_right.setImageResource(rightImage);

                if (leftCard > rightCard) {
                    leftScore += 1;
                    tv_score_left.setText(String.valueOf(leftScore));
                } else if (leftCard < rightCard) {
                    rightScore += 1;
                    tv_score_right.setText(String.valueOf(rightScore));
                } else {
                    Toast.makeText(MainActivity.this, "WAR", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}