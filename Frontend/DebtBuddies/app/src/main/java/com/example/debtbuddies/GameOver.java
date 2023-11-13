package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * Game over screen.
 */
public class GameOver extends AppCompatActivity {
    TextView tv_status;
    War w = new War();
    PlayerStats b = new PlayerStats();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        tv_status = findViewById(R.id.tv_text);
        b = w.p;
        if (w.getWin() == true) {
            tv_status.setText("player 1 wins ");
        } else {
            tv_status.setText("player 2 wins " + w.getWin());
        }

    }
}
