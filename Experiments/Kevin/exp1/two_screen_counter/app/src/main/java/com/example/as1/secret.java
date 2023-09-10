package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class secret extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        CheckBox box1 = findViewById(R.id.checkBox);
        CheckBox box2 = findViewById(R.id.checkBox2);
        CheckBox box3 = findViewById(R.id.checkBox3);
        ImageView star1 = findViewById(R.id.imageView5);
        ImageView star2 = findViewById(R.id.imageView6);
        ImageView star3 = findViewById(R.id.imageView8);
        TextView hiddenMsg = findViewById(R.id.textView2);
        Button backButton = findViewById(R.id.Back_button);
        RadioButton superSecretButton = findViewById(R.id.radioButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(secret.this, CounterActivity.class);
                startActivity(intent);
            }
        });

        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setVisibility(View.VISIBLE);
            }
        });

        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star2.setVisibility(View.VISIBLE);
            }
        });

        final boolean[] secretFound = {false};  // turned into array to avoid weird error

        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secretFound[0]) {
                    star3.setVisibility(View.VISIBLE);
                } else {
                    hiddenMsg.setVisibility(View.VISIBLE);
                }
            }
        });

        superSecretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secretFound[0] = true;
                hiddenMsg.setVisibility(View.INVISIBLE);
            }
        });
    }
}