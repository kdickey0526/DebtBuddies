package com.example.bj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    int playerNumH;
    int playerNumL;
    int dealerNumH;
    int dealerNumL;
    Button b_deal;
    Button b_stand;
    Button b_double;
    ImageView playerCard1;
    ImageView playerCard2;
    ImageView playerCard3;
    ImageView playerCard4;
    ImageView playerCard5;


    ImageView dealerCard1;
    ImageView dealerCard2;
    ImageView dealerCard3;
    ImageView dealerCard4;
    ImageView dealerCard5;

    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b_deal = findViewById(R.id.b_deal);
        b_stand = findViewById(R.id.b_stand);
        b_double = findViewById(R.id.b_double);
        playerCard1 = findViewById(R.id.id_card);
        playerCard2 = findViewById(R.id.id_card2);
        playerCard3 = findViewById(R.id.id_card3);
        playerCard4 = findViewById(R.id.id_card4);
        playerCard5 = findViewById(R.id.id_card5);
        dealerCard1 = findViewById(R.id.id_dealer1);
        dealerCard2 = findViewById(R.id.id_dealer2);
        dealerCard3 = findViewById(R.id.id_dealer3);
        dealerCard4 = findViewById(R.id.id_dealer4);
        dealerCard5 = findViewById(R.id.id_dealer5);
        String card;

        card = hitPlayer();


        int image = getResources().getIdentifier("club" + card, "drawable", getPackageName());
        playerCard1.setImageResource(image);

        card = hitPlayer();

        image = getResources().getIdentifier("club" + card, "drawable", getPackageName());
        playerCard2.setImageResource(image);



        card = hitDealer();
        image = getResources().getIdentifier("club" + card, "drawable", getPackageName());
        dealerCard1.setImageResource(image);

        String display = Integer.toString(playerNumH);


        i = 2;
    }


    public void onDoubleClicked(View view) {

    }

    public void onDealClicked(View view) {
        i++;

        String card = hitPlayer();
        int image = getResources().getIdentifier("club" + card, "drawable", getPackageName());
        if (i == 3) {
            playerCard3.setImageResource(image);
        } else if (i  == 4) {
            playerCard4.setImageResource(image);
        } else if (i  == 5) {
            playerCard5.setImageResource(image);
        }

        if (playerNumH <= 21) {
            String display = Integer.toString(playerNumH);
            Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
        } else if (playerNumL <= 21) {
            playerNumH = playerNumL;
            String display = Integer.toString(playerNumL);
            Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
        } else {
            String display = "BUST";
            Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
        }


    }

    public void onStandClicked (View view) {
        int i = 2;

        String card = hitDealer();

        int image = getResources().getIdentifier("club" + card, "drawable", getPackageName());
        dealerCard2.setImageResource(image);

        while(true) {
            if (dealerNumH >= playerNumH && dealerNumH <= 21 || dealerNumL >= playerNumH && dealerNumL <= 21 || dealerNumH == 21) { //dealer wins
                break;
            } else if (dealerNumL > 21) {   //dealer looses
                break;
            } else {    //dealer hit
                card = hitDealer();
                image = getResources().getIdentifier("club" + card, "drawable", getPackageName());
                if (i == 3) {
                    dealerCard3.setImageResource(image);
                } else if (i  == 4) {
                    dealerCard4.setImageResource(image);
                } else if (i  == 5) {
                    dealerCard5.setImageResource(image);
                }
                i++;

            }
        }
    }


    public String hitPlayer() {

        Random r = new Random();

        int card =  r.nextInt(13) + 1;

        if (card == 11 || card == 12 || card == 13) {
            playerNumH += 10;
            playerNumL += 10;
        } else  if (card == 14) {
            playerNumH += 11;
            playerNumL += 1;
        } else {
            playerNumH += card;
            playerNumL += card;
        }

        if (playerNumH > 21) {
            playerNumH = playerNumL;
        }
        String cards = Integer.toString(card);
        return cards;
    }
    public String hitDealer() {

        Random r = new Random();

        int card =  r.nextInt(13) + 1;

        if (card == 11 || card == 12 || card == 13) {
            dealerNumH += 10;
            dealerNumL += 10;
        } else  if (card == 14) {
            dealerNumH += 11;
            dealerNumL += 1;
        } else {
            dealerNumH += card;
            dealerNumL += card;
        }

        if (dealerNumH > 21) {
            dealerNumH = dealerNumL;
        }
        String cards = Integer.toString(card);
        return cards;
    }
}