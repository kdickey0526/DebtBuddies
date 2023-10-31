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
    Button b_confirm, b_back;
    String username, email, password, confirmPassword, SERVER_URL;
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

        b_confirm = findViewById(R.id.b_confirm);
        b_back = findViewById(R.id.b_back);

        createAccount = false;
    }
    public void onSubmitClicked(View v) {
        username = tv_username.getText().toString();
        email = tv_email.getText().toString();
        password = tv_password.getText().toString();
        confirmPassword = tv_confirmPassword.getText().toString();
        if (password.equals(confirmPassword)) {
            Toast.makeText(this,"account created" ,Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"go back to login" ,Toast.LENGTH_SHORT).show();
            createAccount = true;
        } else {
            Toast.makeText(this,"passwords don't match" ,Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackClicked(View v) {

        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/users/";// + requestedUser;
        if (createAccount = true) {
            postRequest();

            Intent intent = new Intent(this, LoginScreenActivity.class);
            startActivity(intent);
        }
    }

    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
                    String username = response.getString("userName");
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
                        "\"userName\":\"" + username + "\"," +
                        "\"email\":\"" + email +"\"," +
                        "\"password\":\"" + password + "\"" +
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