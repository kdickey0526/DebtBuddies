package com.example.debtbuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * Screen which displays the current user's friends list. Displays their
 * name and whether they are online.
 */
public class FriendsListActivity extends AppCompatActivity {

    private TextView listOfFriends;
    private EditText addingFriend;
    private ScrollView scroller;
    private ImageView btn_refresh;

    private String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/guys/";
    private JSONArray userFriendList = null;
    private JSONObject currentFriend = null;
    private String friendName;
    private static final String TAG = "FriendsListActivity";

    // =============================================================================================================================================
    // not sure how adding a friend wants to be handled, so that part still needs to be implemented
    // =============================================================================================================================================

    /**
     * Runs when the screen is navigated to. Initializes UI elements
     * and fetches the current user's friends.
     * @param savedInstanceState the instance of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        // instantiate views
        listOfFriends = findViewById(R.id.tv_friends);
        addingFriend = findViewById(R.id.et_addUser);
        btn_refresh = findViewById(R.id.btn_refresh_friendslist);
        scroller = findViewById(R.id.scroll_view);
        addingFriend.setFocusableInTouchMode(true);
        addingFriend.setFocusable(true);
        addingFriend.requestFocus();
        addingFriend.setOnKeyListener(new View.OnKeyListener() {
            /**
             * Listener for the chat message box that takes user input. Listens for the enter/return key to be pressed,
             * and then sends the message typed into the box.
             * @param view the EditText message textbox
             * @param i the keycode/key pressed
             * @param keyEvent the state of the key (pressed/down)
             * @return true if enter is pressed false otherwise
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // if user presses enter, send message
                if (!MyApplication.loggedInAsGuest) {
                    if ((keyEvent.getAction()) == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                        String newFriend = addingFriend.getText().toString();
                        newFriend = newFriend.trim();
                        // make sure message being sent isn't just white space
                        if (!newFriend.equals("") || !newFriend.isEmpty()) {
                            // send the message
                            try {
                                postRequest(newFriend);
                                Log.d(TAG, "adding friend successful");
                                scroller.setFocusable(ScrollView.NOT_FOCUSABLE);
                                scroller.post(() -> scroller.fullScroll(View.FOCUS_DOWN));
                                scroller.setFocusable(ScrollView.NOT_FOCUSABLE);
                            } catch (Exception e) {
                                // shouldn't throw any exceptions, but just in case
                                Log.d(TAG, "onKey: Exception when sending message");
                                e.printStackTrace();
                            }
                            addingFriend.setText("");
                            scroller.post(() -> scroller.fullScroll(View.FOCUS_DOWN));
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }

        if (!MyApplication.loggedInAsGuest) {
            // setup server URL
            SERVER_URL += MyApplication.currentUserName; // or something similar

            // request user's friends list from backend here
            makeJsonArrayReq();

            // running theory is that receives json object of ID's of each friend, so grab each friend and append data
            // for each loop for each friend in the list/json object
            // display each user adding onto the text view, similar to how global chat was implemented
            // also display the user's online status

//            try {
//                int i = 0;
//                for (i = 0; i < userFriendList.length(); i++) { // .length returns # of key/value pairs in the object
//                    friendID = userFriendList.getJSONObject(i).getInt("person_id"); // getInt("person_id" + i); // not sure how the friends are mapped in the database, should grab the friend's ID though
//
//                    SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + friendID;
//                    makeFriendJsonObjReq(); // get that friend's info
//
//                    boolean onlineStatus = currentFriend.getBoolean("is_online"); // not sure what it's actually named in backend
//                    String name = currentFriend.getString("name");
//                    String status = "";
//
//                    if (onlineStatus)
//                        status = "Online";
//                    else
//                        status = "Offline";
//
//                    String previousList = listOfFriends.getText().toString();
//                    listOfFriends.setText(previousList + name + "\t\tStatus: " + status + "\n");
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "The userFriendList JSONObject is probably null.");
//                e.printStackTrace();
//                Log.d(TAG, "userFriendList value = " + userFriendList);
//            }
        } else {
            listOfFriends.setText("Silly goose, you're logged in as guest! You have no friends.");
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh the user's friends list
                if (!MyApplication.loggedInAsGuest) {
                    // setup server URL
                    SERVER_URL += MyApplication.currentUserName; // or something similar
                    // request user's friends list from backend here
                    makeJsonArrayReq();
                } else {
                    listOfFriends.setText("Silly goose, you're logged in as guest! You have no friends.");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // enable dark mode if desired
        if (MyApplication.enableDarkMode) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkerlightgray));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }

        // refresh the user's friends list
        if (!MyApplication.loggedInAsGuest) {
            // setup server URL
            SERVER_URL += MyApplication.currentUserName; // or something similar
            // request user's friends list from backend here
            makeJsonArrayReq();
        } else {
            listOfFriends.setText("Silly goose, you're logged in as guest! You have no friends.");
        }
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
                try {
//                    userFriendList = response; //new JSONArray("{\n\t\"Array\": " + response + "\n}"); // store json object
                    try {
                        int i = 0;
                        for (i = 0; i < response.length(); i++) { // .length returns # of key/value pairs in the object
                            friendName = response.getJSONObject(i).getString("guysFriend"); // getInt("person_id" + i); // not sure how the friends are mapped in the database, should grab the friend's ID though

                            SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/" + friendName;
                            makeFriendJsonObjReq(); // get that friend's info

//                            boolean onlineStatus = currentFriend.getBoolean("is_online"); // not sure what it's actually named in backend
//                            String name = currentFriend.getString("name");
//                            String status = "";
//
//                            if (onlineStatus)
//                                status = "Online";
//                            else
//                                status = "Offline";
//
//                            String previousList = listOfFriends.getText().toString();
//                            listOfFriends.setText(previousList + name + "\t\tStatus: " + status + "\n");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "The userFriendList JSONObject is probably null.");
                        e.printStackTrace();
                        Log.d(TAG, "userFriendList value = " + userFriendList);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed ");
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", error.toString());
                    error.printStackTrace();
                }
            });
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }

    /**
     * Makes a json object request with the given user information.
     * Updates some fields on the screen for debugging purposes as well.
     **/
    private void makeFriendJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            /**
             * Updates some text views and instance variables according to the response.
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
//                currentFriend = response; // store json object
                try {
                    boolean onlineStatus = response.getBoolean("online"); // not sure what it's actually named in backend
                    String name = response.getString("name");
                    String status = "";

                    if (onlineStatus)
                        status = "Online";
                    else
                        status = "Offline";

                    String previousList = listOfFriends.getText().toString();
                    listOfFriends.setText(previousList + name + getString(R.string.tab) + getString(R.string.tab) + "Status: " + status + "\n");
                } catch (Exception e) {
                    Log.e(TAG, "error getting fields from object");
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
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
     * Method sets up the JSONObject (which represents the new user) and sends it to the backend.
     */
    private void postRequest(String friend) {

        // Convert input to JSONObject
        JSONObject postBody;
        String temp =
                "{" +
                        "\"guyName\":\"" + MyApplication.currentUserName + "\"," +
                        "\"guysFriend\":\"" + friend + "\"}";

        try {
            postBody = new JSONObject(temp);
            Log.d(TAG, "Succeeded making JSONObject from user input. Object: " + temp);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-309-048.class.las.iastate.edu:8080/guys/",
                postBody,
                new Response.Listener<JSONObject>() {
                    /**
                     * Does nothing, but is required by the listener.
                     * @param response the response from the server. Contains a JSONObject.
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Adding friend successful");

                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Does nothing, but is required by the listener.
                     * @param error the error response from the server. Contains a VolleyError
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Adding friend unsuccessful");
                        error.printStackTrace();
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