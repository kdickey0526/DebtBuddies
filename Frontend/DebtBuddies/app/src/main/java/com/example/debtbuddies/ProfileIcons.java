package com.example.debtbuddies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileIcons extends AppCompatActivity {
    ImageView playerIcon0, playerIcon1, playerIcon2, playerIcon3, playerIcon4,
            playerIcon5, playerIcon6, playerIcon7, playerIcon8, playerIcon9,
            playerIcon10, playerIcon11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);

        playerIcon0 = findViewById(R.id.icon0);
        playerIcon1 = findViewById(R.id.icon1);
        playerIcon2 = findViewById(R.id.icon2);
        playerIcon3 = findViewById(R.id.icon3);
        playerIcon4 = findViewById(R.id.icon4);
        playerIcon5 = findViewById(R.id.icon5);
        playerIcon6 = findViewById(R.id.icon6);
        playerIcon7 = findViewById(R.id.icon7);
        playerIcon8 = findViewById(R.id.icon8);
        playerIcon9 = findViewById(R.id.icon9);
        playerIcon10 = findViewById(R.id.icon10);
        playerIcon11 = findViewById(R.id.icon11);

    }
}
