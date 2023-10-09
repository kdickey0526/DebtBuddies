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


public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private TextView coinCount;
    private TextView msgResponse;
    private Button loginBtn;
    private Button createAcctBtn;


    public String SERVER_URL;

    private JSONObject currentObj;
    private boolean loggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate views & such
        msgResponse = (TextView) findViewById(R.id.loggedInAs);
        coinCount = (TextView) findViewById(R.id.coinCount);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        createAcctBtn = (Button) findViewById(R.id.createAcctButton);
    }

    public void loginBtnOnClickListener(View view) {
        // set SERVER_URL
        String requestedUser = (String) usernameField.getText().toString();
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/users/" + requestedUser; // URL is set by serveraddress/<given username>
        makeJsonObjReq();
    }

    public void createAcctButtonListener(View view) {
        // switch to the create account activity
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Making json object request
     **/
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
                    // grab fields here
                    String username = response.getString("userName");
                    String coins = response.getString("coins");

                    currentObj = response; // store json object as string in currentObj

                    msgResponse.setText("Logged in as: " + username);
                    coinCount.setText("Coins: " + coins);
                    loggedIn = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    public void onIncrementCoinListener(View view) {
        if (loggedIn) {
            int coins;
            try {
                coins = currentObj.getInt("coins");
                coins++;
                currentObj.put("coins", coins);
                postRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            makeJsonObjReq();
        }
    }

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject(currentObj.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                SERVER_URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Volley: ", "object PUT");
                            coinCount.setText("Coins: " + response.getInt("coins"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}