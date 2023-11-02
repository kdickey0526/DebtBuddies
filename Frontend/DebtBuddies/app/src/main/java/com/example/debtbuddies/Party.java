package com.example.debtbuddies;
import static android.text.TextUtils.replace;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

public class Party extends AppCompatActivity {

    String username = "Brock";
    String icon = "icon4";
    ImageView playerIcon;
    TextView playerUsername;
    Button joinParty, createParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        playerUsername = findViewById(R.id.textView);
        playerIcon = findViewById(R.id.icon);
        joinParty = findViewById(R.id.b_join);
        createParty = findViewById(R.id.b_create);

        int image = getResources().getIdentifier(icon, "drawable", getPackageName());
        playerIcon.setImageResource(image);

        playerUsername.setText(username);
    }

    public void onCreate(View v){
        Intent intent = new Intent(this, CreateParty.class);
        startActivity(intent);
    }
    public void onJoin(View v){
        Intent intent = new Intent(this, AcceptInvite.class);
        startActivity(intent);
    }
}


