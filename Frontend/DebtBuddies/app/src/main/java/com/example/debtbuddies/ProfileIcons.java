package com.example.debtbuddies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileIcons extends AppCompatActivity {
    String icon;
    ImageView playerIcon;
    CardView playerIcon0, playerIcon1, playerIcon2, playerIcon3, playerIcon4,
            playerIcon5, playerIcon6, playerIcon7, playerIcon8;

    String SERVER_URL = "";

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
        icon = "icon0";

    }
    public void onButtonClick1(View view) {
        image = getResources().getIdentifier("icon1", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon1";

    }
    public void onButtonClick2(View view) {
        image = getResources().getIdentifier("icon2", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon2";

    }
    public void onButtonClick3(View view) {
        image = getResources().getIdentifier("icon3", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon3";

    }
    public void onButtonClick4(View view) {
        image = getResources().getIdentifier("icon4", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon4";
    }
    public void onButtonClick5(View view) {
        image = getResources().getIdentifier("icon5", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon5";
    }
    public void onButtonClick6(View view) {
        image = getResources().getIdentifier("icon6", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon6";
    }
    public void onButtonClick7(View view) {
        image = getResources().getIdentifier("icon7", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon7";

    }
    public void onButtonClick8(View view) {
        image = getResources().getIdentifier("icon8", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon8";
    }

    public void onMenuClicked(View view) {
        try {
            MyApplication.currentUser.put("icon", icon);
           // postRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody;
        String temp =
                "{" +
                        "\"icon\":\"" + icon + "\"" +
                        "}";

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
