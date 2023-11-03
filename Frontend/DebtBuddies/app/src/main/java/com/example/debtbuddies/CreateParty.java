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
public class CreateParty extends AppCompatActivity {
    Button b_back;
    String member1, member2, member3;

    String SERVER_URL= "";
    EditText tv_member1, tv_member2, tv_member3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        b_back = findViewById(R.id.b_back);

        tv_member1 = findViewById(R.id.member1);
        tv_member2 = findViewById(R.id.member2);
        tv_member3 = findViewById(R.id.member3);
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, Party.class);
        startActivity(intent);
    }

    public void onSendClicked(View v) {
        member1 = tv_member1.getText().toString();
        member2 = tv_member2.getText().toString();
        member3 = tv_member3.getText().toString();

        if (!(member1.equals("Enter Party Member"))) {
            Toast.makeText(this,"Invited " + member1 ,Toast.LENGTH_SHORT).show();
            postRequest();
        }
        if (!(member2.equals("Enter Party Member"))) {
            Toast.makeText(this,"Invited " + member2 ,Toast.LENGTH_SHORT).show();
            member1 = member2;
            postRequest();
        }
        if (!(member2.equals("Enter Party Member"))) {
            member1 = member2;
            Toast.makeText(this,"Invited " + member3 ,Toast.LENGTH_SHORT).show();
            postRequest();
        }
    }
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody;
        String temp =
                "{" +
                        "\"result\":\"" + member1 + "\"" +
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
