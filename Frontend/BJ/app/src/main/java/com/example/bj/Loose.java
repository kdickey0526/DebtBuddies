package com.example.bj;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Loose extends AppCompatActivity {
    private Button replay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loose);

        replay = findViewById(R.id.b_replay_loose);
    }

    public void onReplayLoose(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}