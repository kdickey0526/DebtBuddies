package com.example.debtbuddies;

import android.annotation.SuppressLint;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class War extends AppCompatActivity {
    TextView tvPlayer1, tvPlayer2;
    ImageView cardPlayer1, cardPlayer2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String suit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);

        tvPlayer1 = findViewById(R.id.tv_player1);
        tvPlayer2 = findViewById(R.id.tv_player2);

        cardPlayer1 = findViewById(R.id.id_player1);
        cardPlayer2 = findViewById(R.id.id_player2);

        ArrayList<Card> deck = new ArrayList<Card>();
        ArrayList<Card> player1 = new ArrayList<Card>();
        ArrayList<Card> player2 = new ArrayList<Card>();

        for (int i = 1; i <= 4; i ++) {
            if (i == 1) {
                suit = "heart";
            } else if (i == 2) {
                suit = "diamond";
            } if (i == 3) {
                suit = "club";
            } else {
                suit = "spade";
            }
            for(int j = 2; j <= 14; j ++) {
                Card c = new Card();
                c.createCard(suit, j, suit + j);
                deck.add(c);
            }
        }
        int temp;
        Random r = new Random();
        for (int i = 52; i > 26; i --) {
            temp = r.nextInt(i);
            player1.add(deck.get(temp));
            deck.remove(temp);

        }
        player2 = deck;

        tvPlayer1.setText(String.valueOf(player2.size()));
        tvPlayer2.setText(String.valueOf(player1.size()));
    }
git a

    public void onDealClicked (View view) {

    }
}

