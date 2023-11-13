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

/**
 * Activity for the user to create and register a new account with the backend.
 * Creates a new account with the user's supplied username, email, and password.
 */
public class CreateAccountActivity extends AppCompatActivity {

    // as a note it's almost certainly better to implement this as a fragment and not an activity but at this point
    // i do not really care so this is fine

    private EditText tv_username,tv_email,tv_password,tv_confirmPassword;
    private Button b_save, b_submit;
    private String username, email, password, confirmPassword;
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/add/";
    private String tag_string_req = "string_req";
    private String TAG = LoginScreenActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private Boolean createAccount;

    /**
     * Runs when starting the activity. Initializes all of the instance variables, UI, etc. involved
     * in the activity itself.
     * @param savedInstanceState the state of the app
     */
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
            /**
             * The onClickListener for the "save" button. Gets the user's input and prepares it for parsing
             * into a JSONObject, which will be sent to the backend.
             * @param view
             */
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
            /**
             * The onClickListener for the "submit" button. Sends a POST request to the backend to create
             * the new user given the fields supplied by the user. Once the request is submitted, goes back to
             * the Login screen.
             * @param view
             */
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

    /**
     * Method sets up the JSONObject (which represents the new user) and sends it to the backend.
     */
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody;
        String temp =
                "{" +
                        "\"email\":\"" + email + "\"," +
                        "\"userName\":\"" + username +"\"," +
                        "\"password\":\"" + password + "\"," +
                        "\"person_id\":\"" + 8 +
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
                    /**
                     * Does nothing, but is required by the listener.
                     * @param response the response from the server. Contains a JSONObject.
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        //  tvResponse.setText(response.toString());

                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Does nothing, but is required by the listener.
                     * @param error the error response from the server. Contains a VolleyError
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //tvResponse.setText(error.getMessage());
                    }
                }
        ){
            /**
             * Gets the headers of the request. Not used, but may be required.
             * @return a HashMap of the headers
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            /**
             * Gets the parameters of the request. Not used, but may be required.
             * @return a HashMap of the parameters
             */
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