//package com.example.debtbuddies;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//
//
//public class MainActivityTest extends AppCompatActivity {
//    public String defaultString = "default";
//    public Switch aSwitch;
//
//    private MainActivityPresenter presenter;
//    private TextView myTextView;
//    private ProgressBar progressBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_screen);
//
//        presenter = new MainActivityPresenter(this, getApplicationContext());
//
//        aSwitch = findViewById(R.id.switch1);
//
//        initProgressBar();
//        myTextView = findViewById(R.id.myTextView);
//        final EditText stringEntry = findViewById(R.id.stringEntry);
//
//        Button reverseButton = findViewById(R.id.submit);
//        reverseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (aSwitch.isChecked()){
//                    presenter.revStr(defaultString);
//                }
//                else{
//                    presenter.revStr(stringEntry.getText().toString());
//                }
//            }
//        });
//
//        Button capitalizeButton = findViewById(R.id.submit2);
//        capitalizeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (aSwitch.isChecked()){
//                    presenter.capStr(defaultString);
//                }
//                else{
//                    presenter.capStr(stringEntry.getText().toString());
//                }
//            }
//        });
//    }
//
//    public void test_setPresenter(MainActivityPresenter p) { this.presenter = p;}
//    public MainActivityPresenter test_getPresenter() {return presenter;}
//
//
//    private void initProgressBar() {
//        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
//        progressBar.setIndeterminate(true);
//        RelativeLayout.LayoutParams params
//                = new RelativeLayout.LayoutParams(
//                Resources.getSystem().getDisplayMetrics().widthPixels, 250
//        );
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        this.addContentView(progressBar, params);
//        hideProgressBar();
//    }
//
//    @Override
//    public void updateUserInfoTextView(String info) {
//        myTextView.setText(info);
//    }
//
//    @Override
//    public void showProgressBar() {
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgressBar() {
//        progressBar.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    public String getText(){
//        return myTextView.getText().toString();
//    }
//
//}
