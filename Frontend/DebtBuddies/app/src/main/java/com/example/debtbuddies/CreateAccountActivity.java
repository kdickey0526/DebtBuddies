package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    // as a note it's almost certainly better to implement this as a fragment and not an activity but at this point
    // i do not really care so this is fine


    EditText tv_username,tv_email,tv_password,tv_confirmPassword;
    Button b_save, b_submit;
    String username, email, password, confirmPassword;
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/add";
    private String tag_string_req = "string_req";
    private String TAG = LoginScreenActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    Boolean createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        tv_username = findViewById(R.id.tv_username);
        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        tv_confirmPassword = findViewById(R.id.tv_confirm);

        b_save = findViewById(R.id.b_save);
        b_submit = findViewById(R.id.b_submit);

        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = tv_username.getText().toString();
                email = tv_email.getText().toString();
                password = tv_password.getText().toString();
                confirmPassword = tv_confirmPassword.getText().toString();
                if (password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(),"Data saved. Press SUBMIT." ,Toast.LENGTH_SHORT).show();
                    createAccount = true;
                } else {
                    Toast.makeText(getApplicationContext(),"Passwords don't match. Please try again." ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createAccount = true) {
                    postRequest();

                    Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class);
                    startActivity(intent);
                }
            }
        });

        createAccount = false;
    }

    // Deprecated. Probably remove.
    public void onSaveClicked(View v) {
        username = tv_username.getText().toString();
        email = tv_email.getText().toString();
        password = tv_password.getText().toString();
        confirmPassword = tv_confirmPassword.getText().toString();
        if (password.equals(confirmPassword)) {
            Toast.makeText(this,"Data saved. Press SUBMIT." ,Toast.LENGTH_SHORT).show();
            createAccount = true;
            Log.d(TAG, "saved account information inputted");
        } else {
            Toast.makeText(this,"Passwords don't match. Please try again." ,Toast.LENGTH_SHORT).show();
        }
    }

    // Deprecated. Probably remove.
    public void onSubmitClicked(View v) {
//        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/"; // initialized above
        if (createAccount = true) {
            Log.d(TAG, "posting request");
            postRequest();

            Intent intent = new Intent(this, LoginScreenActivity.class);
            startActivity(intent);
        }
    }

    // Not needed. Probably remove.
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
                    String username = response.getString("name");
                    // grab other fields here

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

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody;
        String temp =
                "{" +
                        "\"email\":\"" + email + "\"," +
                        "\"userName\":\"" + username +"\"," +
                        "\"password\":\"" + password + "\"" +
                        "}";

        try {
             postBody = new JSONObject(temp);
            Log.d(TAG, "Succeeded making JSONObject from user input.");
        } catch (JSONException e) {
            throw new RuntimeException(e);
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

        Log.d(TAG, "Adding POST request to the queue.");
        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}