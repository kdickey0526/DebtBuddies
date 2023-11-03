package com.example.debtbuddies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlackJack extends AppCompatActivity {
    int id;
    String username, email, password;
    String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/users/";
    int playerNumH;
    int playerNumL;
    int dealerNumH;
    int dealerNumL;
    Button b_deal, b_stand, b_double, b_replay, b_menu;
    ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, dealerCard1,
        dealerCard2, dealerCard3, dealerCard4, dealerCard5;
    boolean gameOver, doubleClick, hitPlayer;

    TextView tvDealer, tvPlayer, tvStatus,tvBal, tvBet;
    int i, bal, bet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);


        b_deal = findViewById(R.id.b_deal);
        b_stand = findViewById(R.id.b_stand);
        b_double = findViewById(R.id.b_double);
        b_replay = findViewById(R.id.b_replay);
        playerCard1 = findViewById(R.id.id_card);
        playerCard2 = findViewById(R.id.id_card2);
        playerCard3 = findViewById(R.id.id_card3);
        playerCard4 = findViewById(R.id.id_card4);
        playerCard5 = findViewById(R.id.id_card5);
        dealerCard1 = findViewById(R.id.id_dealer1);
        dealerCard2 = findViewById(R.id.id_dealer2);
        dealerCard3 = findViewById(R.id.id_dealer3);
        dealerCard4 = findViewById(R.id.id_dealer4);
        dealerCard5 = findViewById(R.id.id_dealer5);
        tvDealer = findViewById(R.id.tv_dealer);
        tvPlayer = findViewById(R.id.tv_player);
        tvStatus =  findViewById(R.id.tv_text);
        tvBal = findViewById(R.id.textView);
        tvBet = findViewById(R.id.textView2);
        b_menu = findViewById(R.id.b_menu);
        String card;

        bal = 50;
        bet = 5;
        gameOver = false;

        card = hitPlayer();


        int image = getResources().getIdentifier( card, "drawable", getPackageName());
        playerCard1.setImageResource(image);

        card = hitPlayer();

        image = getResources().getIdentifier(card, "drawable", getPackageName());
        playerCard2.setImageResource(image);



        card = hitDealer();
        image = getResources().getIdentifier(card, "drawable", getPackageName());
        dealerCard1.setImageResource(image);

        tvDealer.setText(String.valueOf(dealerNumH));
        tvPlayer.setText(String.valueOf(playerNumH));
        String temp = "Balance: ";
        temp += bal;
        tvBal.setText(temp);

       String temp2 = "Bet: ";
       temp2 += bet;
       tvBet.setText(temp2);
        i = 2;
        doubleClick = false;
        hitPlayer = false;
    }


    public void onDoubleClicked(View view) {
        if (gameOver == false && doubleClick == false && hitPlayer == false) {


            bet = bet * 2;

            String card = hitPlayer();

            int image = getResources().getIdentifier(card, "drawable", getPackageName());
            playerCard3.setImageResource(image);

            if (playerNumH <= 21) {
                String display = Integer.toString(playerNumH);
                Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            } else if (playerNumL <= 21) {
                playerNumH = playerNumL;
                String display = Integer.toString(playerNumL);
                Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            } else {
                String display = "BUST";
                Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            }

            tvPlayer.setText(String.valueOf(playerNumH));
            tvBet.setText("Bet: " + String.valueOf(bet));
            doubleClick = true;

        }
    }

    public void onDealClicked(View view) {
        if (doubleClick == false) {
            i++;

            String card = hitPlayer();
            int image = getResources().getIdentifier(card, "drawable", getPackageName());
            if (i == 3) {
                playerCard3.setImageResource(image);
            } else if (i == 4) {
                playerCard4.setImageResource(image);
            } else if (i == 5) {
                playerCard5.setImageResource(image);
            }

            if (playerNumH <= 21) {
                String display = Integer.toString(playerNumH);
                Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            } else if (playerNumL <= 21) {
                playerNumH = playerNumL;
                String display = Integer.toString(playerNumL);
                Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            } else {
                String display = "BUST";
                Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            }

            tvPlayer.setText(String.valueOf(playerNumH));
        }
    }
    public void onReplayClicked(View view) {
        if (bet > bal || gameOver == false) {

        } else {

            String card;
            bet = 5;

            playerNumH = 0;
            playerNumL = 0;
            dealerNumH = 0;
            dealerNumL = 0;

            card = hitPlayer();


            int image = getResources().getIdentifier(card, "drawable", getPackageName());
            playerCard1.setImageResource(image);

            card = hitPlayer();

            image = getResources().getIdentifier(card, "drawable", getPackageName());
            playerCard2.setImageResource(image);


            card = hitDealer();
            image = getResources().getIdentifier(card, "drawable", getPackageName());
            dealerCard1.setImageResource(image);

            image = getResources().getIdentifier("cardback", "drawable", getPackageName());
            dealerCard2.setImageResource(image);
            dealerCard3.setImageResource(image);
            dealerCard4.setImageResource(image);
            dealerCard5.setImageResource(image);

            playerCard3.setImageResource(image);
            playerCard4.setImageResource(image);
            playerCard5.setImageResource(image);

            tvDealer.setText(String.valueOf(dealerNumH));
            tvPlayer.setText(String.valueOf(playerNumH));

            tvStatus.setText("");
            String temp = "Balance: ";
            temp += bal;
            tvBal.setText(temp);

            String temp2 = "Bet: ";
            temp2 += bet;
            tvBet.setText(temp2);
            i = 2;
            doubleClick = false;
            gameOver = false;
            hitPlayer = false;
        }
    }
    public void onStandClicked (View view) {
        if (gameOver == true) {

        } else {
            gameOver = true;
        int i = 2;
        int playerNum;
        if (playerNumH <= 21) {
            playerNum = playerNumH;
        } else {
            playerNum = playerNumL;
        }

        String card = hitDealer();

        int image = getResources().getIdentifier(card, "drawable", getPackageName());
        dealerCard2.setImageResource(image);

        while (true) {
            i++;
            if (playerNumH > 21 || playerNumL > 21) { //dealer win
                tvDealer.setText(String.valueOf(dealerNumH));
                bal -= bet;
                break;
            } else if (playerNum == dealerNumH || playerNum == dealerNumL) { //push
                tvDealer.setText(String.valueOf(dealerNumH));
                break;
            }
            if (dealerNumH >= playerNumH && dealerNumH <= 21 || dealerNumL >= playerNumH && dealerNumL <= 21 || dealerNumH == 21) { //dealer wins
                tvDealer.setText(String.valueOf(dealerNumH));
                bal -= bet;
                break;
            } else if (dealerNumL > 21) {   //dealer looses
                tvDealer.setText(String.valueOf(dealerNumH));
                bal += bet;
                break;
            } else {    //dealer hit
                card = hitDealer();
                image = getResources().getIdentifier(card, "drawable", getPackageName());
                if (i == 3) {
                    dealerCard3.setImageResource(image);
                } else if (i == 4) {
                    dealerCard4.setImageResource(image);
                } else if (i == 5) {
                    dealerCard5.setImageResource(image);
                }
            }
            tvDealer.setText(String.valueOf(dealerNumH));
        }

        String temp = "Balance: ";
        temp += bal;
        tvBal.setText(temp);

        String temp2 = "Bet: ";
        temp2 += bet;
        tvBet.setText(temp2);
    }
    }


    public String hitPlayer() {

        hitPlayer = true;
        Random r = new Random();

        int card =  r.nextInt(13) + 2;
        int type = r.nextInt(4) + 1;
        String suit;

        if (card == 11 || card == 12 || card == 13) {
            playerNumH += 10;
            playerNumL += 10;
        } else  if (card == 14) {
            playerNumH += 11;
            playerNumL += 1;
        } else {
            playerNumH += card;
            playerNumL += card;
        }

        if (playerNumH > 21) {
            playerNumH = playerNumL;
        }
        if (type == 1) {
            suit = "club";
        } else if (type == 2) {
            suit = "spade";
        } else if (type == 3) {
            suit = "diamond";
        } else {
            suit = "heart";
        }
        String cards = suit + Integer.toString(card);
        return cards;

    }
    public String hitDealer() {

        Random r = new Random();

        int card =  r.nextInt(13) + 2;
        int type = r.nextInt(4) + 1;
        String suit;

        if (card == 11 || card == 12 || card == 13) {
            dealerNumH += 10;
            dealerNumL += 10;
        } else  if (card == 14) {
            dealerNumH += 11;
            dealerNumL += 1;

        } else if (card > 14) {

        } else {
            dealerNumH += card;
            dealerNumL += card;
        }

        if (dealerNumH > 21) {
            dealerNumH = dealerNumL;
        }
        if (type == 1) {
            suit = "club";
        } else if (type == 2) {
            suit = "spade";
        } else if (type == 3) {
            suit = "diamond";
        } else {
            suit = "heart";
        }
        String cards = suit + Integer.toString(card);
        return cards;
    }

    public void onMenuClicked(View view) {
        if (gameOver == true) {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }
    }
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody;
        String temp =
                "{" +
                        "\"id\":\"" + id + "\"," +
                        "\"userName\":\"" + username + "\"," +
                        "\"email\":\"" + email +"\"," +
                        "\"password\":\"" + password + "\"" +
                        "\"coins\":\"" + bal + "\"" +
                        "}";


        //\"password\":\"MS313Owen\"}";

//                "{\"id\":62,\"userName\":\"Brock\",\"isOnline\":true,\"email\":\"oparker@iastate.edu\",\"password\":\"MS313Owen\",\"coins\":0}";
        try {
            postBody = new JSONObject(temp);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //String postBody = "username:" + username + "password:" + password + "email:" + email;

        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb

        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                SERVER_URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //  tvResponse.setText(response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //tvResponse.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}