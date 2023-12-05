package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/Whackamole"; //person/WhacAMole for old version
    private TextView tv_whacBoard;
    private TextView tv_warBoard;
    private TextView tv_bjBoard;

    private int count = 0;

    private JSONArray leaderboardArray; // will be re-assigned to update each textview
    private JSONObject currentPerson1; // holds current object/person being added to whichever leaderboard
    private JSONObject currentPerson2;
    private JSONObject currentPerson3;
    private JSONObject currentPerson4;
    private JSONObject currentPerson5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // instantiate views
        tv_whacBoard = findViewById(R.id.tv_whacLeaderboard);
        tv_warBoard = findViewById(R.id.tv_warLeaderboard);
        tv_bjBoard = findViewById(R.id.tv_blackjackLeaderboard);

        tv_whacBoard.setText("");
        tv_warBoard.setText("");
        tv_bjBoard.setText("");


        updateWhac();
        count = 0;
//        updateBJ();
        count = 0;
//        updateWar();
        count = 0;
    }

    private void updateWhac() {
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/Whackamole"; // will have to be updated after basic testing
        makeJsonArrayReq();
//        while (leaderboardArray == null) {
//            makeJsonArrayReq();
//            Log.d(TAG, "trying to get the JSONArray again");
//        }
//        int i;
//        try {
//            for (i = 0; i < leaderboardArray.length(); i++) { // should only give 5 objects based on size in backend
//                try {
//                    currentPerson = leaderboardArray.getJSONObject(i);
//                    String name = currentPerson.getString("name");
//                    String s = tv_whacBoard.getText().toString();
//                    tv_whacBoard.setText(s + (i + 1) + ": " + name + "\n");
//
//                } catch (JSONException e) {
//                    Log.e(TAG, "Failed getting the JSONObject or elements of it within the array");
//                    e.printStackTrace();
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "leaderboardArray probably null");
//            e.printStackTrace();
//        }

    }

    private void updateWar() {

    }

    private void updateBJ() {

    }


    /**
     * Makes a json array request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonArrayReq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, "http://coms-309-048.class.las.iastate.edu:8080/person/Whackamole", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Volley Response", "response received: " + response.toString());
//                try {
//                    leaderboardArray = response; //new JSONArray("{\n\t\"Array\": " + response + "\n}"); // store json object
//
//                } catch (Exception e) {
//                    Log.e(TAG, "Failed setting leaderboardArray");
//                    e.printStackTrace();
//                }
                try {
                    int one = response.getJSONObject(0).getInt("id");
                    int two = response.getJSONObject(1).getInt("id");
                    int three = response.getJSONObject(2).getInt("id");
                    int four = response.getJSONObject(3).getInt("id");
                    int five = response.getJSONObject(4).getInt("id");
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + one;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + two;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + three;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + four;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + five;
                    makeJsonObjReq();

                } catch (JSONException e) {
                    Log.e(TAG, "Error setting person");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("array_broke", error.toString());
                error.printStackTrace();
            }
        });

        Log.d(TAG, jsonArrReq.toString());
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }

    /**
     * Makes a json object request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                switch(count) {
                    case 0: // get first user
                        try {
                            String s = tv_whacBoard.getText().toString();
                            tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
                            count++;
                            break;
                        } catch (Exception e) {

                        }
                    case 1: // get second user
                        try {
                            String s = tv_whacBoard.getText().toString();
                            tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
                            count++;
                            break;
                        } catch (Exception e) {
                            Log.e(TAG, "error updating leaderboard text");
                            e.printStackTrace();
                        }
                    case 2: // get third user
                        try {
                            String s = tv_whacBoard.getText().toString();
                            tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
                            count++;
                            break;
                        } catch (Exception e) {
                            Log.e(TAG, "error updating leaderboard text");
                            e.printStackTrace();
                        }
                    case 3: // get fourth user
                        try {
                            String s = tv_whacBoard.getText().toString();
                            tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
                            count++;
                            break;
                        } catch (Exception e) {
                            Log.e(TAG, "error updating leaderboard text");
                            e.printStackTrace();
                        }
                    case 4: // get fifth user
                        try {
                            String s = tv_whacBoard.getText().toString();
                            tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
                            count++;
                            break;
                        } catch (Exception e) {
                            Log.e(TAG, "error updating leaderboard text");
                            e.printStackTrace();
                        }
                    default:
                        break;
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

}