package com.example.experiment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private int kittyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.nextBtn);
        ImageView kitty1 = (ImageView) findViewById(R.id.imageView);
        ImageView kitty2 = (ImageView) findViewById(R.id.imageView2);
        ImageView kitty3 = (ImageView) findViewById(R.id.imageView3);
        ImageView kitty4 = (ImageView) findViewById(R.id.imageView4);
        ImageView kitty5 = (ImageView) findViewById(R.id.imageView5);
        ImageView kitty6 = (ImageView) findViewById(R.id.imageView6);
        ImageView kitty7 = (ImageView) findViewById(R.id.imageView7);
        ImageView kitty8 = (ImageView) findViewById(R.id.imageView8);
        ImageView kitty9 = (ImageView) findViewById(R.id.imageView9);
        ImageView kitty10 = (ImageView) findViewById(R.id.imageView10);
        ImageView kitty11 = (ImageView) findViewById(R.id.imageView11);
        ImageView kitty12 = (ImageView) findViewById(R.id.imageView12);
        ImageView kitty13 = (ImageView) findViewById(R.id.imageView13);
        ImageView kitty14 = (ImageView) findViewById(R.id.imageView14);

        ImageView arr[] = new ImageView[14];
        arr[0] = kitty1;
        arr[1] = kitty2;
        arr[2] = kitty3;
        arr[3] = kitty4;
        arr[4] = kitty5;
        arr[5] = kitty6;
        arr[6] = kitty7;
        arr[7] = kitty8;
        arr[8] = kitty9;
        arr[9] = kitty10;
        arr[10] = kitty11;
        arr[11] = kitty12;
        arr[12] = kitty13;
        arr[13] = kitty14;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show kitty, prompt for new kitty
                if (kittyCount == 0) {
                    button.setText(R.string.next);
                    arr[arr.length-1].setVisibility(View.INVISIBLE);
                    arr[kittyCount].setVisibility(View.VISIBLE);
                    kittyCount++;
                    return;
                }

                if (kittyCount <= arr.length-1) {
                    arr[kittyCount].setVisibility(View.VISIBLE);
                    arr[kittyCount - 1].setVisibility(View.INVISIBLE);

                    if (kittyCount == arr.length-1) {
                        kittyCount = 0;
                        button.setText(R.string.rat);
                    } else {
                        kittyCount++;
                    }

                }
            }
        });
    }
}