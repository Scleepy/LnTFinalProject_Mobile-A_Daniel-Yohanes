package com.daniel.courseapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {

    SharedPreferences sPLogin, sPUser;

    TextView nameTextView, dateTextView, emailTextView, courseIDTextView, nameCardTextView, courseCardIDtextView, timeTodayTextView, totalTimeTextView, countingTextview, totalExpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        View view = window.getDecorView();

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= 27) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        sPLogin = this.getSharedPreferences("LOGININFO", MODE_PRIVATE);
        sPUser = this.getSharedPreferences("USERINFO", MODE_PRIVATE);

        setContentView(R.layout.activity_user_profile);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Utility utility = new Utility();

        String name = sPUser.getString("NAME", "");
        String date = sPUser.getString("DATE", "");
        String email = sPUser.getString("EMAIL", "");
        String courseID = sPUser.getString("COURSEID", "");
        String progressCounter = sPUser.getString("PROGRESSCOUNTER", "");
        String timeToday = sPUser.getString("PROGRESSTODAY", "");
        String timeTotal = sPUser.getString("PROGRESSTOTAL", "");
        String totalExp = sPUser.getString("TOTALEXP", "");

        String shortName = utility.getName(name);

        nameTextView = findViewById(R.id.nameTextProfile);
        dateTextView = findViewById(R.id.joinedDateText);
        emailTextView = findViewById(R.id.emailTextProfile);
        courseIDTextView = findViewById(R.id.courseIDTextProfile);
        nameCardTextView = findViewById(R.id.userNameCourseCard);
        courseCardIDtextView = findViewById(R.id.courseIDCourseCard);
        timeTodayTextView = findViewById(R.id.timeTodayContent);
        totalTimeTextView = findViewById(R.id.timeTotalContent);
        countingTextview = findViewById(R.id.countingProgressContent);
        totalExpTextView = findViewById(R.id.expProgressContent);

        if(name.length() > 15){
            nameTextView.setText(shortName);
        } else {
            nameTextView.setText(name);
        }

        dateTextView.setText("Joined " + date);
        emailTextView.setText(email);
        courseIDTextView.setText(courseID);

        nameCardTextView.setText(shortName);
        courseCardIDtextView.setText(courseID);

        timeTodayTextView.setText(timeToday + " Minute(s)");
        totalTimeTextView.setText(timeTotal + " Minute(s)");
        countingTextview.setText(progressCounter);
        totalExpTextView.setText(totalExp);


    }

    public void attemptLogout(View view){
        SharedPreferences.Editor editorLogin = sPLogin.edit();
        editorLogin.clear();
        editorLogin.apply();

        SharedPreferences.Editor editorUser = sPUser.edit();
        editorUser.clear();
        editorUser.apply();

        Toast.makeText(view.getContext(), "Logout successful", Toast.LENGTH_SHORT).show();

        HomeScreen.homeScreenActivity.finish();

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void backButtonProfile(View view){
        super.onBackPressed();
    }

}