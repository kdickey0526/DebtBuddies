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

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private TextView msgResponse;
    private Button loginBtn;
    private Button createAcctBtn;

                                    // vv URL below not setup to our server yet
    public String SERVER_URL; // = "http://10.0.2.2:8080/users/1"; // access table of users


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
    }

    public void loginBtnOnClickListener(View view) {
        // set SERVER_URL
        String requestedUser = (String) usernameField.getText().toString();
        SERVER_URL = "http://10.0.2.2:8080/users/" + requestedUser; // URL is set by serveraddress/<given username>
        makeJsonObjReq(); // sets text for "Logged in as: "
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
                    String username = response.getString("username");
                    // grab other fields here

                    msgResponse.setText(username);

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
}