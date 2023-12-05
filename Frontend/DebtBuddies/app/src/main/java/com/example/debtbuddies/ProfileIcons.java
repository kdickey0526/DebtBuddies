package com.example.debtbuddies;

import static java.lang.Math.floor;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

/**
 * Screen showing the different profile icons a user can select. Currently 9 options.
 */
public class ProfileIcons extends AppCompatActivity {
    private static final String TAG = "ProfileIcons";
    String icon;
    ImageView playerIcon;
    CardView playerIcon0, playerIcon1, playerIcon2, playerIcon3, playerIcon4,
            playerIcon5, playerIcon6, playerIcon7, playerIcon8;

    String SERVER_URL = "http://coms-309-048.class.las.iastate.edu:8080/person/";

    Button b_icon0, b_icon1, b_icon2, b_icon3, b_icon4, b_icon5, b_icon6, b_icon7,
            b_icon8, b_frag;
    int image;

    /**
     * initlize
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icons);

        playerIcon = findViewById(R.id.icon);
        playerIcon0 = findViewById(R.id.icon0);
        playerIcon1 = findViewById(R.id.icon1);
        playerIcon2 = findViewById(R.id.icon2);
        playerIcon3 = findViewById(R.id.icon3);
        playerIcon4 = findViewById(R.id.icon4);
        playerIcon5 = findViewById(R.id.icon5);
        playerIcon6 = findViewById(R.id.icon6);
        playerIcon7 = findViewById(R.id.icon7);
        playerIcon8 = findViewById(R.id.icon8);


        b_icon0 = findViewById(R.id.b_icon0);
        b_icon1 = findViewById(R.id.b_icon1);
        b_icon2 = findViewById(R.id.b_icon2);
        b_icon3 = findViewById(R.id.b_icon3);
        b_icon4 = findViewById(R.id.b_icon4);
        b_icon5 = findViewById(R.id.b_icon5);
        b_icon6 = findViewById(R.id.b_icon6);
        b_icon7 = findViewById(R.id.b_icon7);
        b_icon8 = findViewById(R.id.b_icon8);
        b_frag = findViewById(R.id.b_menu);

        if (!MyApplication.loggedInAsGuest) {
            try {
                SERVER_URL += MyApplication.currentUser.getString("name");
            } catch (Exception e) {
                Log.d(TAG, "Not logged in as guest, failed to get name field from currentUser");
            }
        }
        makeJsonObjReq();



//        getSupportFragmentManager().beginTransaction().add(R.id.frag_menu, new FirstFragment()),commit();
    }

    /**
     * save the icon choice and leave
     * @param view
     */
//    public void menu(View view) {
//        try {
//            MyApplication.currentUser.put("Profile", icon);
//            postRequest();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(this, Menu.class);
//        startActivity(intent);
//    }

    /**
     * pick icon  0
     * @param view
     */
    public void onButtonClick0(View view) {
        image = getResources().getIdentifier("icon0", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon0";

    }
    /**
     * pick icon 1
     * @param view
     */
    public void onButtonClick1(View view) {
        image = getResources().getIdentifier("icon1", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon1";

    }
    /**
     * pick icon 2
     * @param view
     */
    public void onButtonClick2(View view) {
        image = getResources().getIdentifier("icon2", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon2";

    }
    /**
     * pick icon 3
     * @param view
     */
    public void onButtonClick3(View view) {
        image = getResources().getIdentifier("icon3", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon3";

    }
    /**
     * pick icon 4
     * @param view
     */
    public void onButtonClick4(View view) {
        image = getResources().getIdentifier("icon4", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon4";
    }
    /**
     * pick icon 5
     * @param view
     */
    public void onButtonClick5(View view) {
        image = getResources().getIdentifier("icon5", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon5";
    }
    /**
     * pick icon 6
     * @param view
     */
    public void onButtonClick6(View view) {
        image = getResources().getIdentifier("icon6", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon6";
    }
    /**
     * pick icon 7
     * @param view
     */
    public void onButtonClick7(View view) {
        image = getResources().getIdentifier("icon7", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon7";

    }
    /**
     * pick icon 8
     * @param view
     */
    public void onButtonClick8(View view) {
        image = getResources().getIdentifier("icon8", "drawable", getPackageName());
        playerIcon.setImageResource(image);
        icon = "icon8";
    }

    /**
     * sends the icon to the server
     * goes to the menu
     * @param view
     */
    public void onMenuClicked(View view) {
        try {
            MyApplication.currentUser.put("Profile", icon);
            postRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }


    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response", "response received: " + response.toString());
                try {
                    // grab fields here
                    String profileIcon = response.getString("Profile");
                    image = getResources().getIdentifier(profileIcon, "drawable", getPackageName());
                    playerIcon.setImageResource(image);

                    MyApplication.currentUser = response; // store json object

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
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject(MyApplication.currentUser.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                SERVER_URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Volley: ", "object PUT");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
