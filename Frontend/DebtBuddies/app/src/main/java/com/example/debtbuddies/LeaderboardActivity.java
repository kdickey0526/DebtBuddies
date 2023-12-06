package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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

//    private int count = 0;
    private boolean updatingWhack = true;
    private boolean updatingWar = true;
    private boolean updatingBJ = true;


    // deprecated, from an approach that did not work
//    private JSONArray leaderboardArray; // will be re-assigned to update each textview
//    private JSONObject currentPerson1; // holds current object/person being added to whichever leaderboard
//    private JSONObject currentPerson2;
//    private JSONObject currentPerson3;
//    private JSONObject currentPerson4;
//    private JSONObject currentPerson5;



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
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }

        updatingWhack = true;
        updateWhac();
        updatingWhack = false;
        updatingBJ = true;
        updateBJ();
        updatingBJ = false;
        updatingWar = true;
        updateWar();
        updatingWar = false;
    }

    @Override
    protected void onResume() {
        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }
        super.onResume();
    }

    private void updateWhac() {
//        count = 0;
//        updatingWhack = true;
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/Whackamole"; // may be wrong
        makeJsonArrayReq_WHACK();
//        updatingWhack = false;
//        count = 0;
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
//        count = 0;
//        updatingBJ = true;
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/BlackJack";    // may be wrong
        makeJsonArrayReq_BJ();
//        updatingBJ = false;
//        count = 0;
    }
    private void updateWar() {
//        count = 0;
//        updatingWar = true;
        SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/War";      // may be wrong
        makeJsonArrayReq_WAR();
//        updatingWar = false;
//        count = 0;
    }




    /**
     * Makes a json array request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonArrayReq_WHACK() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Volley Response", "response received: " + response.toString());
                // leaderboard must have 5 people in it for this design
                try {
                    Log.d(TAG, "updating whacamole leaderboard");
                    tv_whacBoard.setText("");
                    tv_whacBoard.setText("1st: " + response.getJSONObject(0).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(0).getInt("whack") + "\n");
                    String s = tv_whacBoard.getText().toString();
                    tv_whacBoard.setText(s + "2nd: " + response.getJSONObject(1).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(1).getInt("whack") + "\n");
                    s = tv_whacBoard.getText().toString();
                    tv_whacBoard.setText(s + "3rd: " + response.getJSONObject(2).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(2).getInt("whack") + "\n");
                    s = tv_whacBoard.getText().toString();
                    tv_whacBoard.setText(s + "4th: " + response.getJSONObject(3).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(3).getInt("whack") + "\n");
                    s = tv_whacBoard.getText().toString();
                    tv_whacBoard.setText(s + "5th: " + response.getJSONObject(4).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(4).getInt("whack") + "\n");
//                  updatingWhack = false;
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching fields from each object");
                    e.printStackTrace();
                }

//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + one;
//                    makeJsonObjReq();
//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + two;
//                    makeJsonObjReq();
//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + three;
//                    makeJsonObjReq();
//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + four;
//                    makeJsonObjReq();
//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/num/" + five;
//                    makeJsonObjReq();
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
     * Makes a json array request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonArrayReq_BJ() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Volley Response", "response received: " + response.toString());
                // leaderboard must have 5 people in it for this design
                try {
                    Log.d(TAG, "updating blackjack leaderboard");
                    tv_bjBoard.setText("1st: " + response.getJSONObject(0).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(0).getInt("blackJack") + "\n");
                    String s = tv_bjBoard.getText().toString();
                    tv_bjBoard.setText(s + "2nd: " + response.getJSONObject(1).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(1).getInt("blackJack") + "\n");
                    s = tv_bjBoard.getText().toString();
                    tv_bjBoard.setText(s + "3rd: " + response.getJSONObject(2).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(2).getInt("blackJack") + "\n");
                    s = tv_bjBoard.getText().toString();
                    tv_bjBoard.setText(s + "4th: " + response.getJSONObject(3).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(3).getInt("blackJack") + "\n");
                    s = tv_bjBoard.getText().toString();
                    tv_bjBoard.setText(s + "5th: " + response.getJSONObject(4).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(4).getInt("blackJack") + "\n");
//                    updatingBJ = false;
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching fields from each object");
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
     * Makes a json array request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeJsonArrayReq_WAR() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Volley Response", "response received: " + response.toString());
                // leaderboard must have 5 people in it for this design
                try {
                    Log.d(TAG, "updating war leaderboard");
                    tv_warBoard.setText("1st: " + response.getJSONObject(0).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(0).getInt("warWon") + "\n");
                    String s = tv_warBoard.getText().toString();
                    tv_warBoard.setText(s + "2nd: " + response.getJSONObject(1).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(1).getInt("warWon") + "\n");
                    s = tv_warBoard.getText().toString();
                    tv_warBoard.setText(s + "3rd: " + response.getJSONObject(2).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(2).getInt("warWon") + "\n");
                    s = tv_warBoard.getText().toString();
                    tv_warBoard.setText(s + "4th: " + response.getJSONObject(3).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(3).getInt("warWon") + "\n");
                    s = tv_warBoard.getText().toString();
                    tv_warBoard.setText(s + "5th: " + response.getJSONObject(4).getString("userName") + getString(R.string.tab) + getString(R.string.tab) + response.getJSONObject(4).getInt("warWon") + "\n");
                    updatingWar = false;
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching fields from each object");
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

    // no longer needed with reordering of backend
//    /**
//     * Makes a json object request with the given user information.
//     * Updates some fields on the screen for debugging purposes as well.
//     **/
//    private void makeJsonObjReq() {
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
//            /**
//             * Updates some text views and instance variables according to the response.
//             * @param response
//             */
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("Volley Response", "response received: " + response.toString());
//
//                if (updatingWhack) {
//                    switch(count) {
//                        case 0: // get first user
//                            try {
//                                String s = tv_whacBoard.getText().toString();
//                                tv_whacBoard.setText(s + response.getString("name") + "\t:\t" + response.getString("whack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 1: // get second user
//                            try {
//                                String s = tv_whacBoard.getText().toString();
//                                tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 2: // get third user
//                            try {
//                                String s = tv_whacBoard.getText().toString();
//                                tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 3: // get fourth user
//                            try {
//                                String s = tv_whacBoard.getText().toString();
//                                tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 4: // get fifth user
//                            try {
//                                String s = tv_whacBoard.getText().toString();
//                                tv_whacBoard.setText(s + response.getString("name") + "\t" + response.getString("whack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        default:
//                            break;
//                    }
//                } else if (updatingBJ) {
//                    switch(count) {
//                        case 0: // get first user
//                            try {
//                                String s = tv_bjBoard.getText().toString();
//                                tv_bjBoard.setText(s + response.getString("name") + "\t:\t" + response.getString("blackJack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 1: // get second user
//                            try {
//                                String s = tv_bjBoard.getText().toString();
//                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 2: // get third user
//                            try {
//                                String s = tv_bjBoard.getText().toString();
//                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 3: // get fourth user
//                            try {
//                                String s = tv_bjBoard.getText().toString();
//                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 4: // get fifth user
//                            try {
//                                String s = tv_bjBoard.getText().toString();
//                                tv_bjBoard.setText(s + response.getString("name") + "\t" + response.getString("blackJack") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        default:
//                            break;
//                    }
//                } else if (updatingWar) {
//                    switch(count) {
//                        case 0: // get first user
//                            try {
//                                String s = tv_warBoard.getText().toString();
//                                tv_warBoard.setText(s + response.getString("name") + "\t:\t" + response.getString("war") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 1: // get second user
//                            try {
//                                String s = tv_warBoard.getText().toString();
//                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 2: // get third user
//                            try {
//                                String s = tv_warBoard.getText().toString();
//                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 3: // get fourth user
//                            try {
//                                String s = tv_warBoard.getText().toString();
//                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        case 4: // get fifth user
//                            try {
//                                String s = tv_warBoard.getText().toString();
//                                tv_warBoard.setText(s + response.getString("name") + "\t" + response.getString("war") + "\n");
//                                count++;
//                                break;
//                            } catch (Exception e) {
//                                Log.e(TAG, "error updating leaderboard text");
//                                e.printStackTrace();
//                            }
//                        default:
//                            break;
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            /**
//             * Displays the error to Logcat.
//             * @param error
//             */
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Volley Error", error.toString());
//            }
//        });
//
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
//    }

    public void onRefreshButtonClick(View view) {
        updateWhac();
        updateBJ();
        updateWar();
    }

}