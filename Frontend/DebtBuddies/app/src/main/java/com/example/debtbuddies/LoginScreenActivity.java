package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * The login screen. Takes a user's username and password and sends it to the database to login. Checking done in the backend.
 */
public class LoginScreenActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private TextView coinCount;
    private TextView msgResponse;
    private Button loginBtn;
    private Button createAcctBtn;
    private static final String TAG = "LoginScreenActivity";
    public String SERVER_URL;
    private boolean passFailed = false;
    private boolean loggedIn;

    /**
     *  Initializes UI elements of the login screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // instantiate views & such
        msgResponse = (TextView) findViewById(R.id.loggedInAs);
        coinCount = (TextView) findViewById(R.id.coinCount);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        createAcctBtn = (Button) findViewById(R.id.createAcctButton);
    }

    /**
     * Listener for the "Login"/"Save" button.
     * @param view the button
     */
    public void loginBtnOnClickListener(View view) {
        // set SERVER_URL
        String requestedUser = (String) usernameField.getText().toString();

//        if (requestedUser.equals("guest")) {
//            MyApplication.loggedInAsGuest = true;
//        }

        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + requestedUser;
        makeJsonObjReq();

        // at this point (no pass detection) should not occur so long as the provided
        // username exists and is found.
        if (MyApplication.currentUser == null) {
            Log.d(TAG, "loginBtnOnClickListener: currentUser was null");

            // allow to login as "guest" for special purposes
            if (!usernameField.getText().toString().equals("guest")) {
//                Toast.makeText(this, "Username/password was incorrect or no user entered. Please try again.", Toast.LENGTH_SHORT).show();
                loginBtn.setText("Login");
                return;
            }

            Log.d(TAG, "loginBtnOnClickListener: logged in as guest");
            MyApplication.loggedInAsGuest = true;
        }

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    /**
     * Listener for the create account button. Launches the create account screen.
     * @param view the button
     */
    public void createAcctButtonListener(View view) {
        // switch to the create account activity
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Makes a json object request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
                    // grab fields here
                    String username = response.getString("name");
                    String coins = response.getString("coins");
                    MyApplication.currentUserName = response.getString("name");

                    // insert code here to save the response into a text file
                    MyApplication.currentUser = response; // store json object

                    msgResponse.setText("Logged in as: " + username);
                    coinCount.setText("Coins: " + coins);
                    loggedIn = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            /**
             * Displays the error to Logcat.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    /**
     * Listener for the "+" button. Increments the user's coin value and sends the updated value to the backend.
     * Just used for debugging, not part of the final product.
     * @param view
     */
    public void onIncrementCoinListener(View view) {
        if (loggedIn) {
            int coins;
            try {
                coins = MyApplication.currentUser.getInt("coins");
                coins++;
                MyApplication.currentUser.put("coins", coins);
                postRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            makeJsonObjReq();
        }
    }

    /**
     * Sends a JSONObject to the backend. Updates values based on the currentUser.
     */
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject(MyApplication.currentUser.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                SERVER_URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    /**
                     * Updates the text field "Coins: " when the response is received.
                     * @param response the response from the backend
                     */
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
                    /**
                     * Sends the volley error to Logcat.
                     * @param error the error
                     */
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