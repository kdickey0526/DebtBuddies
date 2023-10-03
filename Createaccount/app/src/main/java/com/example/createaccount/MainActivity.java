package com.example.createaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;


public class MainActivity extends AppCompatActivity {
EditText tv_username,tv_email,tv_password,tv_confirmPassword;
Button b_confirm;
String username, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_username = findViewById(R.id.tv_username);
        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        tv_confirmPassword = findViewById(R.id.tv_confirm);

        b_confirm = findViewById(R.id.b_confirm);


    }

    public void onSubmitClicked(View v) {
        username = tv_username.getText().toString();
        email = tv_email.getText().toString();
        password = tv_password.getText().toString();
        confirmPassword = tv_confirmPassword.getText().toString();
        if (password.equals(confirmPassword)) {

        } else {
            Toast.makeText(this,"passwords don't match" ,Toast.LENGTH_SHORT).show();
        }
    }


}