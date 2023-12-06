package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/Whackamole"; //person/WhacAMole for old version
    private TextView tv_whacBoard;
    private TextView tv_warBoard;
    private TextView tv_bjBoard;
    private ImageView btn_refresh;
    private ConstraintLayout overall_background;

    private int count = 0;
    private boolean updatingWhack;
    private boolean updatingWar;
    private boolean updatingBJ;


    // deprecated, from an approach that did not work
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
        btn_refresh = findViewById(R.id.btn_refresh);

        tv_whacBoard.setText("");
        tv_warBoard.setText("");
        tv_bjBoard.setText("");
        if (MyApplication.enableDarkMode) {
            overall_background.setBackgroundColor(ContextCompat.getColor(this, R.color.darkerlightgray));
        }

        updateWhac();
        updateBJ();
        updateWar();
    }

    private void updateWhac() {
        count = 0;
        updatingWhack = true;
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/Whackamole"; // may be wrong
        makeJsonArrayReq();
        updatingWhack = false;
        count = 0;
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

    private void updateBJ() {
        count = 0;
        updatingBJ = true;
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/Blackjack";    // may be wrong
        makeJsonArrayReq();
        updatingBJ = false;
        count = 0;
    }
    private void updateWar() {
        count = 0;
        updatingWar = true;
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/War";      // may be wrong
        makeJsonArrayReq();
        updatingWar = false;
        count = 0;
    }




    /**
     * Makes a json array request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonArrayReq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONArray>() {
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
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + one;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + two;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + three;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + four;
                    makeJsonObjReq();
                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + five;
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

                if (updatingWhack) {
                    switch(count) {
                        case 0: // get first user
                            try {
                                String s = tv_whacBoard.getText().toString();
                                tv_whacBoard.setText(s + response.getString("name") + "\t:\t" + response.getString("whack") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
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
                } else if (updatingBJ) {
                    switch(count) {
                        case 0: // get first user
                            try {
                                String s = tv_bjBoard.getText().toString();
                                tv_bjBoard.setText(s + response.getString("name") + "\t:\t" + response.getString("blackJack") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 1: // get second user
                            try {
                                String s = tv_bjBoard.getText().toString();
                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 2: // get third user
                            try {
                                String s = tv_bjBoard.getText().toString();
                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 3: // get fourth user
                            try {
                                String s = tv_bjBoard.getText().toString();
                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 4: // get fifth user
                            try {
                                String s = tv_bjBoard.getText().toString();
                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        default:
                            break;
                    }
                } else if (updatingWar) {
                    switch(count) {
                        case 0: // get first user
                            try {
                                String s = tv_warBoard.getText().toString();
                                tv_warBoard.setText(s + response.getString("name") + "\t:\t" + response.getString("war") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 1: // get second user
                            try {
                                String s = tv_warBoard.getText().toString();
                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 2: // get third user
                            try {
                                String s = tv_warBoard.getText().toString();
                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 3: // get fourth user
                            try {
                                String s = tv_warBoard.getText().toString();
                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
                                count++;
                                break;
                            } catch (Exception e) {
                                Log.e(TAG, "error updating leaderboard text");
                                e.printStackTrace();
                            }
                        case 4: // get fifth user
                            try {
                                String s = tv_warBoard.getText().toString();
                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
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

    public void onRefreshButtonClick(View view) {
        updateWhac();
        updateBJ();
        updateWar();
    }

}