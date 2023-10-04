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

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.example.debtbuddies.app.AppController;


public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private TextView msgResponse;
    private Button loginBtn;
    private Button createAcctBtn;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
                                         // vv URL below not setup to our server yet
    public String SERVER_URL; // = "http://10.0.2.2:8080/users/1"; // access table of users
    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate views & such
        msgResponse = (TextView) findViewById(R.id.loggedInAs);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        createAcctBtn = (Button) findViewById(R.id.createAcctButton);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
    }

    public void loginBtnOnClickListener(View view) {
        // set SERVER_URL
        if (!usernameField.getText().toString().equals(null) && !passwordField.getText().toString().equals(null)) {
            SERVER_URL = "http://10.0.2.2:8080/users/" + usernameField.getText().toString(); // URL is set by serveraddress/<given username>
            makeStringReq(); // sets text for "Logged in as: "
        }
    }

    public void createAcctButtonListener(View view) {
        // switch to the create account activity
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Making json object request
     **/
    private void makeStringReq() {
        showProgressDialog();
                                                        // SERVER_URL should be modified to link to whatever
                                                        // user is being logged into.
        StringRequest strReq = new StringRequest(Method.GET, SERVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                // need to check password matches here too before logging in appropriately

                msgResponse.setText("Logged in as: " + response.toString());
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
}